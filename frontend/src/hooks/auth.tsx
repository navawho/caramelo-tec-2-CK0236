import React, { createContext, useCallback, useContext, useState } from 'react';
import api from '../services/api';

interface Credentials {
	username: string;
	password: string;
}

interface AuthContextData {
	token: string;
	signIn(credentials: Credentials): Promise<void>;
	signOut(): void;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

const AuthProvider: React.FC = ({ children }) => {
	const [token, setToken] = useState(() => {
		const userToken = localStorage.getItem('@Caramelo:token');

		if (userToken) {
			return userToken;
		}

		return '';
	});

	const signIn = useCallback(async ({ username, password }) => {
		const response = await api.post<{ token: string }>('sign-in', {
			username,
			password,
		});

		const { token: userToken } = response.data;

		localStorage.setItem('@Caramelo:token', userToken);

		setToken(userToken);
	}, []);

	const signOut = useCallback(() => {
		localStorage.removeItem('@Caramelo:token');

		setToken('');
	}, []);

	return (
		<AuthContext.Provider value={{ token, signIn, signOut }}>
			{children}
		</AuthContext.Provider>
	);
};

function useAuth(): AuthContextData {
	const context = useContext(AuthContext);

	if (!context) {
		throw new Error('useAuth must be used within an AuthProvider');
	}

	return context;
}

export { AuthProvider, useAuth };
