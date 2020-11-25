import React, { useEffect, useState } from 'react';
import { FiSearch, FiCheck } from 'react-icons/fi';
import { useToast } from '../../hooks/toast';

import Input from '../../components/InputWithoutUnform';

import api from '../../services/api';

import {
	Container,
	Content,
	Filters,
	Filter,
	FilterOptions,
	Pets,
	SidebarContainer,
	OutContainer,
	Radio,
} from './styles';

import CardPet from '../../components/CardPet';
import Sidebar from '../../components/Sidebar';
import { useAuth } from '../../hooks/auth';
import Pet from '../../interfaces/Pet';

const SignUp: React.FC = () => {
	const [name, setName] = useState('');
	const [pets, setPets] = useState<Pet[]>([]);
	const [port, setPort] = useState('');
	const [type, setType] = useState('');
	const [sex, setSex] = useState('');

	const { addToast } = useToast();
	const { token } = useAuth();

	useEffect(() => {
		const params: {
			port?: string;
			type?: string;
			sex?: string;
			name?: string;
		} = {};

		if (port) {
			params.port = port;
		}

		if (type) {
			params.type = type;
		}

		if (sex) {
			params.sex = sex;
		}

		if (name) {
			params.name = name;
		}

		api
			.get('/pets/adopt', {
				headers: { Authorization: `Bearer ${token}` },
				params,
			})
			.then(({ data }) => {
				setPets(data);
			});
	}, [name, port, sex, token, type]);

	return (
		<>
			<OutContainer>
				<SidebarContainer>
					<Sidebar />
				</SidebarContainer>

				<Container>
					<Content>
						<Filters>
							<Input
								value={name}
								type="text"
								placeholder="Pesquise por nome"
								icon={FiSearch}
								onChange={(e) => setName(e.target.value)}
							/>
							<Filter>
								<h4>Porte</h4>
								<FilterOptions>
									<Radio
										isChecked={port === 'Pequeno'}
										onClick={() => {
											setPort((oldPort) =>
												oldPort === 'Pequeno' ? '' : 'Pequeno',
											);
										}}
									>
										<FiCheck color="#fff" />
									</Radio>
									<label>Pequeno</label>
									<Radio
										isChecked={port === 'Médio'}
										onClick={() => {
											setPort((oldPort) =>
												oldPort === 'Médio' ? '' : 'Médio',
											);
										}}
									>
										<FiCheck color="#fff" />
									</Radio>
									<label>Médio</label>
									<Radio
										isChecked={port === 'Grande'}
										onClick={() => {
											setPort((oldPort) =>
												oldPort === 'Grande' ? '' : 'Grande',
											);
										}}
									>
										<FiCheck color="#fff" />
									</Radio>
									<label>Grande</label>
								</FilterOptions>
							</Filter>
							<Filter>
								<h4>Tipo</h4>
								<FilterOptions>
									<Radio
										isChecked={type === 'Cachorro'}
										onClick={() => {
											setType((oldType) =>
												oldType === 'Cachorro' ? '' : 'Cachorro',
											);
										}}
									>
										<FiCheck color="#fff" />
									</Radio>
									<label>Cachorro</label>
									<Radio
										isChecked={type === 'Gato'}
										onClick={() => {
											setType((oldType) => (oldType === 'Gato' ? '' : 'Gato'));
										}}
									>
										<FiCheck color="#fff" />
									</Radio>
									<label>Gato</label>
								</FilterOptions>
							</Filter>
							<Filter>
								<h4>Sexo</h4>
								<FilterOptions>
									<Radio
										isChecked={sex === 'Macho'}
										onClick={() => {
											setSex((oldSex) => (oldSex === 'Macho' ? '' : 'Macho'));
										}}
									>
										<FiCheck color="#fff" />
									</Radio>
									<label>Macho</label>
									<Radio
										isChecked={sex === 'Fêmea'}
										onClick={() => {
											setSex((oldSex) => (oldSex === 'Fêmea' ? '' : 'Fêmea'));
										}}
									>
										<FiCheck color="#fff" />
									</Radio>
									<label>Fêmea</label>
								</FilterOptions>
							</Filter>
						</Filters>
						<Pets>
							{pets.map((pet) => (
								<CardPet
									key={pet.id}
									isDisabled={false}
									buttonName="Me adote!"
									pet={pet}
									handleClickButton={async () => {
										try {
											await api.post(
												`/solicitations/${pet.id}`,
												{},
												{
													headers: { Authorization: `Bearer ${token}` },
												},
											);

											setPets(
												pets.filter((filteredPet) => filteredPet.id !== pet.id),
											);

											addToast({
												type: 'sucess',
												title: 'Solicitação de adoção realizada com sucesso!',
											});
										} catch (err) {
											addToast({
												type: 'error',
												title: 'Erro na solicitação',
												description:
													'Ocorreu um erro ao solicitar adoção, tente novamente.',
											});
										}
									}}
								/>
							))}
						</Pets>
					</Content>
				</Container>
			</OutContainer>
		</>
	);
};

export default SignUp;
