import React from 'react';
import { BrowserRouter } from 'react-router-dom';

import GlobalStyle from './styles/global';

import Routes from './routes';

import { AuthProvider } from './hooks/auth';
import { ToastProvider } from './hooks/toast';

const App: React.FC = () => (
	<ToastProvider>
		<AuthProvider>
			<BrowserRouter>
				<Routes />
				<GlobalStyle />
			</BrowserRouter>
		</AuthProvider>
	</ToastProvider>
);

export default App;
