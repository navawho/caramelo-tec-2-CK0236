import React, { useEffect, useState, useCallback } from 'react';
import CardPetEdit from '../../components/CardPetEdit';
import Sidebar from '../../components/Sidebar';
import ModalAddPet from '../../components/ModalAddPet';
import { useToast } from '../../hooks/toast';
import { useAuth } from '../../hooks/auth';
import api from '../../services/api';

import {
	Container,
	Content,
	PetWrapper,
	Solicitations,
	Solicitation,
	ButtonWrapper,
	Pets,
	SidebarContainer,
} from './styles';
import Pet from '../../interfaces/Pet';
import SolicitationInterface from '../../interfaces/Solicitation';
import ModalUpdatePet from '../../components/ModalUpdatePet';
import Tooltip from '../../components/Tooltip';

interface PetWithSolicitations extends Pet {
	solicitations: SolicitationInterface[];
}

interface UpdateFormData {
	name?: string;
	imageUrl?: string;
	description?: string;
	port?: string;
	sex?: string;
	type?: string;
}

const Registration: React.FC = () => {
	const [modalOpen, setModalOpen] = useState(false);
	const [updateModalOpen, setUpdateModalOpen] = useState(false);
	const [pets, setPets] = useState<PetWithSolicitations[]>([]);
	const [editingPet, setEditingPet] = useState({} as Pet);

	const { addToast } = useToast();
	const { token } = useAuth();

	const fetchPets = useCallback(async (): Promise<void> => {
		const { data } = await api.get('/pets/my-pets', {
			headers: { Authorization: `Bearer ${token}` },
		});
		const promises = data.map(async (item: Pet) => {
			const { data: solicitations } = await api.get(
				`/solicitations/${item.id}`,
				{ headers: { Authorization: `Bearer ${token}` } },
			);

			return { ...item, solicitations };
		});
		const mappedData: PetWithSolicitations[] = await Promise.all(promises);
		setPets(mappedData);
	}, [token]);

	useEffect(() => {
		fetchPets();
	}, [fetchPets]);

	async function handleAddPet(
		pet: Omit<Pet, 'id' | 'user' | 'available'>,
	): Promise<void> {
		try {
			const response = await api.post(
				'/pets',
				{ ...pet },
				{ headers: { Authorization: `Bearer ${token}` } },
			);

			setPets([...pets, response.data]);

			addToast({ type: 'sucess', title: 'Pet cadastrado com sucesso!' });
		} catch (err) {
			addToast({
				type: 'error',
				title: 'Erro na criação',
				description: 'Ocorreu um erro ao criar o pet, tente novamente.',
			});
		}
	}

	function toggleModal(): void {
		setModalOpen(!modalOpen);
	}

	function toggleUpdateModal(): void {
		setUpdateModalOpen(!updateModalOpen);
	}

	function handleEditPet(pet: Pet): void {
		setEditingPet(pet);
		toggleUpdateModal();
	}

	async function handleRemovePet(petId: number): Promise<void> {
		try {
			await api.delete(`/pets/${petId}`, {
				headers: { Authorization: `Bearer ${token}` },
			});

			setPets((oldPets) => oldPets.filter((pet) => pet.id !== petId));

			addToast({ type: 'sucess', title: 'Pet removido com sucesso!' });
		} catch (err) {
			addToast({
				type: 'error',
				title: 'Erro na deleção',
				description:
					'Ocorreu um erro ao tentar deletar o pet, por favor tente novamente',
			});
		}
	}

	async function handleUpdatePet(pet: Pet): Promise<void> {
		try {
			const updateData: UpdateFormData = {};

			if (pet.name !== editingPet.name) {
				updateData.name = pet.name;
			}

			if (pet.imageUrl !== editingPet.imageUrl) {
				updateData.imageUrl = pet.imageUrl;
			}

			if (pet.description !== editingPet.description) {
				updateData.description = pet.description;
			}

			if (pet.port !== editingPet.port) {
				updateData.port = pet.port;
			}

			if (pet.sex !== editingPet.sex) {
				updateData.sex = pet.sex;
			}

			if (pet.type !== editingPet.type) {
				updateData.type = pet.type;
			}

			const { data } = await api.patch(
				`/pets/${editingPet.id}`,
				{ ...updateData },
				{ headers: { Authorization: `Bearer ${token}` } },
			);

			setPets(
				pets.map((mappedPet) =>
					mappedPet.id === editingPet.id ? { ...data } : mappedPet,
				),
			);

			addToast({ type: 'sucess', title: 'Pet atualizado com sucesso!' });
		} catch (err) {
			addToast({
				type: 'error',
				title: 'Erro na atualização',
				description:
					'Ocorreu um erro ao tentar atualizar o pet, por favor tente novamente',
			});
		}
	}

	async function handleAcceptSolicitation(
		solicitationId: number,
		petName: string,
		username: string,
	): Promise<void> {
		try {
			await api.patch(
				`/solicitations/${solicitationId}/accept`,
				{},
				{ headers: { Authorization: `Bearer ${token}` } },
			);

			api.delete(`/solicitations/${solicitationId}`, {
				headers: { Authorization: `Bearer ${token}` },
			});

			const mappedPets = pets.map((pet) => {
				const solicitationIndex = pet.solicitations.findIndex(
					(solicitation) => solicitation.id === solicitationId,
				);

				if (solicitationIndex === -1) {
					return pet;
				}
				const filteredSolicitations = pet.solicitations.filter(
					(solicitation) => solicitation.id !== solicitationId,
				);

				return { ...pet, solicitations: filteredSolicitations };
			});

			setPets(mappedPets);

			addToast({
				type: 'sucess',
				title: 'Solicitação de adoção aceita',
				description: `${petName} foi adotado por ${username}`,
			});
		} catch (err) {
			addToast({
				type: 'error',
				title: 'Erro ao aceitar a solicitação',
				description:
					'Ocorreu um erro ao aceitar a solicitação, por favor tente novamente',
			});
		}
	}

	async function handleRefuseSolicitation(
		solicitationId: number,
		petName: string,
		username: string,
	): Promise<void> {
		try {
			await api.delete(`/solicitations/${solicitationId}`, {
				headers: { Authorization: `Bearer ${token}` },
			});

			const mappedPets = pets.map((pet) => {
				const solicitationIndex = pet.solicitations.findIndex(
					(solicitation) => solicitation.id === solicitationId,
				);

				if (solicitationIndex === -1) {
					return pet;
				}
				const filteredSolicitations = pet.solicitations.filter(
					(solicitation) => solicitation.id !== solicitationId,
				);

				return { ...pet, solicitations: filteredSolicitations };
			});

			setPets(mappedPets);

			addToast({
				type: 'sucess',
				title: 'Solicitação de adoção recusada',
				description: `Você não deixou ${username} adotar ${petName}`,
			});
		} catch (err) {
			addToast({
				type: 'error',
				title: 'Erro ao aceitar a solicitação',
				description:
					'Ocorreu um erro ao aceitar a solicitação, por favor tente novamente',
			});
		}
	}

	return (
		<>
			<ModalAddPet
				isOpen={modalOpen}
				setIsOpen={toggleModal}
				handleAddPet={handleAddPet}
			/>
			<ModalUpdatePet
				isOpen={updateModalOpen}
				setIsOpen={toggleUpdateModal}
				handleUpdatePet={handleUpdatePet}
				pet={editingPet}
			/>
			<Container>
				<SidebarContainer>
					<Sidebar />
				</SidebarContainer>
				<Content>
					<button
						className="action-button"
						type="button"
						onClick={() => setModalOpen(true)}
					>
						Cadastrar Pet
					</button>
					<Pets>
						{pets &&
							pets.map((pet) => (
								<PetWrapper key={pet.id}>
									<CardPetEdit
										pet={pet}
										handleRemoveButton={handleRemovePet}
										handleUpdateButton={handleEditPet}
									/>
									<Solicitations>
										{pet.solicitations &&
											pet.solicitations.map((solicitation) => (
												<Solicitation>
													<span>
														<Tooltip
															title={`${solicitation.user.email} ${solicitation.user.phone}`}
														>
															<strong>{solicitation.user.username}</strong>
														</Tooltip>
														&nbsp;quer adotar&nbsp;
														<strong>{pet.name}</strong>
													</span>
													<ButtonWrapper>
														<button
															className="action-button"
															type="button"
															onClick={() => {
																handleAcceptSolicitation(
																	solicitation.id,
																	pet.name,
																	solicitation.user.username,
																);
															}}
														>
															Aceitar
														</button>
														<button
															className="action-button"
															type="button"
															onClick={() => {
																handleRefuseSolicitation(
																	solicitation.id,
																	pet.name,
																	solicitation.user.username,
																);
															}}
														>
															Recusar
														</button>
													</ButtonWrapper>
												</Solicitation>
											))}
									</Solicitations>
								</PetWrapper>
							))}
					</Pets>
				</Content>
			</Container>
		</>
	);
};

export default Registration;
