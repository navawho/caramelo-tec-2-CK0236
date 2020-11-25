import React, { useRef, useCallback } from 'react';
import { FiUser, FiLock } from 'react-icons/fi';
import { Link, useHistory } from 'react-router-dom';
import { Form } from '@unform/web';
import { FormHandles } from '@unform/core';
import * as Yup from 'yup';

import Input from '../../components/Input';

import { Container, Content, Card, Inputs } from './styles';

import Logo from '../../assets/Logo.png';
import { useAuth } from '../../hooks/auth';
import { useToast } from '../../hooks/toast';
import getValidationErrors from '../../utils/getValidationErrors';

interface SignInFormData {
	username: string;
	password: string;
}

const SignIn: React.FC = () => {
	const formRef = useRef<FormHandles>(null);

	const { signIn } = useAuth();
	const { addToast } = useToast();

	const history = useHistory();

	const handleSubmit = useCallback(
		async (data: SignInFormData) => {
			try {
				formRef.current?.setErrors({});

				const schema = Yup.object().shape({
					username: Yup.string().required('Username obrigatório'),
					password: Yup.string().required('Senha obrigatória'),
				});

				await schema.validate(data, { abortEarly: false });

				await signIn({ username: data.username, password: data.password });

				addToast({
					type: 'sucess',
					title: 'Sucesso na autenticação',
					description: 'Login realizado com sucesso.',
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
					title: 'Erro na autenticação',
					description: 'Ocorreu um erro ao fazer login, cheque as credenciais.',
				});
			}
		},
		[addToast, history, signIn],
	);

	return (
		<Container>
			<Content>
				<img src={Logo} alt="Caramelo" />
				<Card>
					<h3>Entrar</h3>
					<Form ref={formRef} onSubmit={handleSubmit}>
						<Inputs>
							<label htmlFor="username">Username</label>
							<Input
								type="text"
								id="username"
								name="username"
								placeholder="Seu username"
								icon={FiUser}
							/>
							<label htmlFor="password">Senha</label>
							<Input
								type="password"
								id="password"
								name="password"
								placeholder="Sua senha"
								icon={FiLock}
							/>
							<button className="action-button" type="submit">
								Entrar
							</button>
							<Link to="/sign-up">Ainda não possui conta? Cadastre-se</Link>
						</Inputs>
					</Form>
				</Card>
			</Content>
		</Container>
	);
};

export default SignIn;
