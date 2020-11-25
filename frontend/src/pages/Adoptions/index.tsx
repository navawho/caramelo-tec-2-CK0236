import React, { useEffect, useState } from 'react';

import CardPet from '../../components/CardPet';
import Sidebar from '../../components/Sidebar';
import { useAuth } from '../../hooks/auth';
import Adoption from '../../interfaces/Adoption';
import Solicitation from '../../interfaces/Solicitation';
import { useToast } from '../../hooks/toast';

import api from '../../services/api';

import { Container, Content, LeftContent, RightContent } from './styles';

const Adoptions: React.FC = () => {
	const [adoptions, setAdoptions] = useState<Adoption[]>([]);
	const [solicitations, setSolicitations] = useState<Solicitation[]>([]);

	const { addToast } = useToast();
	const { token } = useAuth();

	useEffect(() => {
		api
			.get('/adoptions', {
				headers: { Authorization: `Bearer ${token}` },
			})
			.then(({ data }) => {
				setAdoptions(data);
			});
		api
			.get('/solicitations', {
				headers: { Authorization: `Bearer ${token}` },
			})
			.then(({ data }) => {
				setSolicitations(data);
			});
	}, [token]);

	return (
		<Container>
			<Sidebar />
			<Content>
				<LeftContent>
					<h2>Adoções</h2>
					<div className="cardContainer">
						{adoptions.map((adoption) => (
							<CardPet
								key={adoption.id}
								buttonName={adoption.returned ? 'Retornado' : 'Retornar'}
								isDisabled={adoption.returned}
								pet={adoption.pet}
								handleClickButton={async () => {
									try {
										await api.patch(
											`/adoptions/${adoption.id}/return`,
											{},
											{
												headers: { Authorization: `Bearer ${token}` },
											},
										);

										setAdoptions(
											adoptions.filter((item) => item.id !== adoption.id),
										);

										addToast({
											type: 'sucess',
											title: 'Retorno de pet efetuado com sucesso.',
										});
									} catch (err) {
										addToast({
											type: 'error',
											title: 'Erro no retorno',
											description: 'Ocorreu um erro ao retornar o Pet',
										});
									}
								}}
							/>
						))}
					</div>
				</LeftContent>

				<RightContent>
					<h2>Solicitações</h2>
					<div className="cardContainer">
						{solicitations.map((solicitation) => (
							<CardPet
								key={solicitation.id}
								buttonName="Cancelar solicitação"
								pet={solicitation.pet}
								handleClickButton={async () => {
									try {
										await api.delete(`/solicitations/${solicitation.id}`, {
											headers: { Authorization: `Bearer ${token}` },
										});

										setSolicitations(
											solicitations.filter(
												(item) => item.id !== solicitation.id,
											),
										);

										addToast({
											type: 'sucess',
											title: 'Solicitação cancelada com sucesso',
										});
									} catch (err) {
										addToast({
											type: 'error',
											title: 'Erro na solicitação',
											description:
												'Ocorreu um erro ao cancelar solicitação, por favor tente novamente',
										});
									}
								}}
							/>
						))}
					</div>
				</RightContent>
			</Content>
		</Container>
	);
};

export default Adoptions;
