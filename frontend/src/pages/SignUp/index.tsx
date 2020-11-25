import React, { useRef, useCallback } from 'react';
import { FiMail, FiLock, FiUser, FiPhone } from 'react-icons/fi';
import { Form } from '@unform/web';
import { Link, useHistory } from 'react-router-dom';
import { FormHandles } from '@unform/core';
import * as Yup from 'yup';

import { useToast } from '../../hooks/toast';
import Input from '../../components/Input';
import api from '../../services/api';
import Logo from '../../assets/Logo.png';
import getValidationErrors from '../../utils/getValidationErrors';

import { Container, Content, Card, Inputs } from './styles';

interface SignUpFormData {
	username: string;
	email: string;
	password: string;
	confirmPassword: string;
	phone: string;
}

const SignUp: React.FC = () => {
	const formRef = useRef<FormHandles>(null);

	const history = useHistory();
	const { addToast } = useToast();

	const handleSubmit = useCallback(
		async (data: SignUpFormData) => {
			try {
				formRef.current?.setErrors({});

				const schema = Yup.object().shape({
					username: Yup.string()
						.required('Username obrigatório')
						.min(4, 'Username deve ter no mínimo 4 caracteres'),
					password: Yup.string()
						.required('Senha obrigatória')
						.min(6, 'Senha deve ter no mínimo 6 caracteres.'),
					email: Yup.string()
						.required('E-mail obrigatório')
						.email('E-mail inválido'),
					phone: Yup.string()
						.required('Telefone obrigatório')
						.matches(
							new RegExp(
								'^(([\\d]{4}[-.\\s]{1}[\\d]{2,3}[-.\\s]{1}[\\d]{2}[-.\\s]{1}[\\d]{2})|([\\d]{4}[-.\\s]{1}[\\d]{3}[-.\\s]{1}[\\d]{4})|((\\(?\\+?[0-9]{2}\\)?\\s?)?(\\(?\\d{2}\\)?\\s?)?\\d{4,5}[-.\\s]?\\d{4}))$',
							),
							'Telefone inválido',
						),
				});

				await schema.validate(data, { abortEarly: false });

				await api.post('/sign-up', data);

				addToast({
					type: 'sucess',
					title: 'Sucesso no cadastro',
					description: 'Você já pode realizar login.',
				});

				history.push('/');
			} catch (err) {
				if (err instanceof Yup.ValidationError) {
					const errors = getValidationErrors(err);

					formRef.current?.setErrors(errors);

					return;
				}

				addToast({
					type: 'error',
					title: 'Erro no cadastro',
					description: 'Ocorreu um erro ao fazer cadastro, tente novamente.',
				});
			}
		},
		[addToast, history],
	);

	return (
		<Container>
			<Content>
				<img src={Logo} alt="Caramelo" />
				<Card>
					<h3>Cadastrar</h3>
					<Form ref={formRef} onSubmit={handleSubmit}>
						<Inputs>
							<Input
								type="text"
								id="username"
								name="username"
								placeholder="Seu username"
								icon={FiUser}
							/>

							<Input
								type="email"
								id="email"
								name="email"
								placeholder="seu@email.com"
								icon={FiMail}
							/>

							<Input
								type="number"
								id="phone"
								name="phone"
								placeholder="(99) 9999-9999"
								icon={FiPhone}
							/>

							<div className="password-wrapper">
								<Input
									type="password"
									id="password"
									name="password"
									placeholder="Sua senha"
									icon={FiLock}
								/>
							</div>

							<Input
								type="password"
								id="confirmPassword"
								name="confirmPassword"
								placeholder="Confirme sua senha"
								icon={FiLock}
							/>

							<button className="action-button" type="submit">
								Cadastrar
							</button>
							<Link to="/sign-in">Já possui conta? Faça login</Link>
						</Inputs>
					</Form>
				</Card>
			</Content>
		</Container>
	);
};

export default SignUp;
