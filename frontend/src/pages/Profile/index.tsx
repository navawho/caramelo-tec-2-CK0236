import React, { useCallback, useRef, useState, useEffect } from 'react';
import { FiLock, FiMail, FiPhone } from 'react-icons/fi';
import { Form } from '@unform/web';
import { FormHandles } from '@unform/core';
import * as Yup from 'yup';

import Input from '../../components/Input';
import api from '../../services/api';
import { useToast } from '../../hooks/toast';
import { useAuth } from '../../hooks/auth';

import { Container, Content, SidebarContainer } from './styles';
import Sidebar from '../../components/Sidebar';
import getValidationErrors from '../../utils/getValidationErrors';

interface UpdateProfileFormData {
	username?: string;
	email?: string;
	phone?: string;
	oldPassword?: string;
	password?: string;
	confirmPassword?: string;
}

const Profile: React.FC = () => {
	const formRef = useRef<FormHandles>(null);
	const [profile, setProfile] = useState({
		username: '',
		email: '',
		phone: '',
	});
	const { token } = useAuth();

	useEffect(() => {
		api
			.get('/users', { headers: { Authorization: `Bearer ${token}` } })
			.then(({ data }) => {
				const { username, email, phone } = data;

				setProfile({
					username,
					email,
					phone,
				});
			});
	}, [token]);

	const { addToast } = useToast();

	const handleSubmit = useCallback(
		async (data: UpdateProfileFormData) => {
			try {
				formRef.current?.setErrors({});

				const schema = Yup.object().shape({
					username: Yup.string().min(
						4,
						'Username deve ter no mínimo 4 caracteres',
					),

					email: Yup.string().email('E-mail inválido'),
					phone: Yup.string(),
					oldPassword: Yup.string(),
					password: Yup.string(),
					confirmPassword: Yup.string(),
				});

				await schema.validate(data, { abortEarly: false });

				const updateData: UpdateProfileFormData = {};

				if (profile.username !== data.username) {
					updateData.username = data.username;
				}

				if (profile.email !== data.email) {
					updateData.email = profile.email;
				}

				if (profile.phone !== data.phone) {
					updateData.phone = profile.phone;
				}

				if (data.oldPassword) {
					updateData.oldPassword = data.oldPassword;
					updateData.password = data.password;
					updateData.confirmPassword = data.confirmPassword;
				}

				await api.patch(
					'/users',
					{ ...updateData },
					{
						headers: { Authorization: `Bearer ${token}` },
					},
				);

				addToast({
					type: 'sucess',
					title: 'Sucesso na atualização',
					description: 'Dados do seu perfil atualizados com sucesso.',
				});
			} catch (err) {
				if (err instanceof Yup.ValidationError) {
					const errors = getValidationErrors(err);

					formRef.current?.setErrors(errors);

					return;
				}

				addToast({
					type: 'error',
					title: 'Erro na atualização',
					description:
						'Ocorreu um erro ao atualizar seus dados, tente novamente.',
				});
			}
		},
		[addToast, profile, token],
	);

	return (
		<Container>
			<SidebarContainer>
				<Sidebar />
			</SidebarContainer>

			<Content>
				<Form ref={formRef} onSubmit={handleSubmit}>
					<legend>Meu perfil</legend>
					<Input
						type="text"
						id="username"
						name="username"
						placeholder="Seu username"
						defaultValue={profile.username}
						icon={FiLock}
					/>
					<Input
						type="email"
						id="email"
						name="email"
						placeholder="Seu e-mail"
						defaultValue={profile.email}
						icon={FiMail}
					/>
					<div className="phone">
						<Input
							type="text"
							id="phone"
							name="phone"
							placeholder="Seu telefone"
							defaultValue={profile.phone}
							icon={FiPhone}
						/>
					</div>
					<Input
						type="password"
						id="oldPassword"
						name="oldPassword"
						placeholder="Senha atual"
						icon={FiLock}
					/>
					<Input
						type="password"
						id="password"
						name="password"
						placeholder="Nova senha"
						icon={FiLock}
					/>
					<Input
						type="password"
						id="confirmPassword"
						name="confirmPassword"
						placeholder="Confirme a nova senha"
						icon={FiLock}
					/>
					<button className="action-button" type="submit">
						Confirmar mudanças
					</button>
				</Form>
			</Content>
		</Container>
	);
};

export default Profile;
