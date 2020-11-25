import React, { useRef, useCallback } from 'react';
import { FiImage } from 'react-icons/fi';

import { FormHandles } from '@unform/core';
import { Form } from './styles';
import Modal from '../Modal';
import Input from '../Input';
import Pet from '../../interfaces/Pet';

interface CreatePetData {
	name: string;
	port: 'Pequeno' | 'Médio' | 'Grande';
	type: 'Cachorro' | 'Gato';
	sex: 'Macho' | 'Fêmea';
	description: string;
	birthDate: string;
	imageUrl: string;
}

interface ModalProps {
	isOpen: boolean;
	setIsOpen: () => void;
	handleAddPet: (pet: Omit<Pet, 'id' | 'available' | 'user'>) => void;
}

const ModalAddPet: React.FC<ModalProps> = ({
	isOpen,
	setIsOpen,
	handleAddPet,
}) => {
	const formRef = useRef<FormHandles>(null);

	const handleSubmit = useCallback(
		async (data: CreatePetData) => {
			console.log(data);
			handleAddPet(data);

			setIsOpen();
		},
		[handleAddPet, setIsOpen],
	);

	return (
		<Modal isOpen={isOpen} setIsOpen={setIsOpen}>
			<Form ref={formRef} onSubmit={handleSubmit}>
				<h1>Novo Pet</h1>
				<Input name="imageUrl" placeholder="Cole o link aqui" icon={FiImage} />

				<Input name="name" placeholder="Nome" />
				<Input name="port" placeholder="Porte" />
				<Input name="sex" placeholder="Sexo" />
				<Input name="type" placeholder="Tipo" />
				<Input name="birthDate" placeholder="Data de nascimento ANO-MES-DIA" />

				<Input name="description" placeholder="Descrição" />
				<button type="submit" className="action-button">
					Enviar
				</button>
			</Form>
		</Modal>
	);
};

export default ModalAddPet;
