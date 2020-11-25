import React from 'react';
import Pet from '../../interfaces/Pet';

import { Container } from './styles';

interface Props {
	pet: Pet;
	handleUpdateButton:(pet: Pet) => void;
	handleRemoveButton:(petId: number) => void;
}

const CardPet: React.FC<Props> = ({ pet, handleRemoveButton, handleUpdateButton }) => {
	return (
		<Container>
			<div className="box-1">
				<img
					src={pet.imageUrl}
					alt={pet.name}
				/>
				<div>
					<h3>{pet.name}</h3>
					<div>
						<label>Porte: </label>
						<span>{pet.port}</span>
					</div>
					<div>
						<label>Tipo: </label>
						<span>{pet.type}</span>
					</div>
					<div>
						<label>Sexo: </label>
						<span>{pet.sex}</span>
					</div>
				</div>
			</div>
			<label>Descrição</label>
			<p>{pet.description}</p>
			<div className="button-wrapper">
				<button className="action-button" type="button" onClick={() => {handleUpdateButton(pet)}}>
					Editar
				</button>
				<button className="action-button" type="button" onClick={() => {handleRemoveButton(pet.id)}}>
					Remover
				</button>
			</div>
		</Container>
	);
};

export default CardPet;
