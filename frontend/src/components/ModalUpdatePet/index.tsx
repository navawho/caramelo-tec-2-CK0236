import React, { useRef, useCallback } from 'react';
import { FiImage } from 'react-icons/fi';

import { FormHandles } from '@unform/core';
import { Form } from './styles';
import Modal from '../Modal';
import Input from '../Input';
import Pet from '../../interfaces/Pet';

interface ModalProps {
	isOpen: boolean;
	setIsOpen: () => void;
	handleUpdatePet: (pet: Pet) => void;
	pet: Pet;
}

const ModalUpdatePet: React.FC<ModalProps> = ({
	isOpen,
	setIsOpen,
	handleUpdatePet,
	pet,
}) => {
	const formRef = useRef<FormHandles>(null);

	const handleSubmit = useCallback(
		async (data: Pet) => {
			handleUpdatePet(data);

			setIsOpen();
		},
		[handleUpdatePet, setIsOpen],
	);

	return (
		<Modal isOpen={isOpen} setIsOpen={setIsOpen}>
			<Form ref={formRef} onSubmit={handleSubmit} initialData={pet}>
				<h1>Novo Pet</h1>

				<label>Imagem</label>
				<Input name="imageUrl" placeholder="Cole o link aqui" icon={FiImage}  />

				<label>Nome</label>
				<Input name="name" placeholder="Nome do seu pet"  />

				<label>Porte</label>
				<Input name="port" placeholder="Grande | Médio | Pequeno" />

				<label>Sexo</label>
				<Input name="sex" placeholder="Macho | Fêmea" />

				<label>Tipo</label>
				<Input name="type" placeholder="Cachorro | Gato" />

				<label>Data de nascimento</label>
				<Input name="birthDate" placeholder="2020-02-02" />

				<label>Descrição</label>
				<Input name="description" placeholder="Descrição" />

				<button type="submit" className="action-button">
					Atualizar
				</button>
			</Form>
		</Modal>
	);
};

export default ModalUpdatePet;
