import React from 'react';
import {
	Redirect,
	Route as ReactDOMRouter,
	RouteProps as ReactDOMRouterProps,
} from 'react-router-dom';

import { useAuth } from '../hooks/auth';

interface RouteProps extends ReactDOMRouterProps {
	isPrivate?: boolean;
	component: React.ComponentType;
}

const Route: React.FC<RouteProps> = ({
	isPrivate = false,
	component: Component,
	...rest
}) => {
	const { token } = useAuth();

	return (
		<ReactDOMRouter
			{...rest}
			render={({ location }) => {
				return isPrivate === !!token ? (
					<Component />
				) : (
					<Redirect
						to={{
							pathname: isPrivate ? '/sign-in' : '/',
							state: { from: location },
						}}
					/>
				);
			}}
		/>
	);
};

export default Route;
