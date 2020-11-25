import React from 'react';
import { Switch } from 'react-router-dom';

import Dashboard from '../pages/Dashboard';
import SignIn from '../pages/SignIn';
import SignUp from '../pages/SignUp';
import Profile from '../pages/Profile';
import Adoptions from '../pages/Adoptions';
import Registrations from '../pages/Registrations';

import Route from './Route';

const Routes: React.FC = () => (
	<Switch>
		<Route path="/" exact component={Dashboard} isPrivate />
		<Route path="/adoptions" exact component={Adoptions} isPrivate />
		<Route path="/sign-in" exact component={SignIn} />
		<Route path="/sign-up" exact component={SignUp} />
		<Route path="/profile" exact component={Profile} isPrivate />
		<Route path="/registrations" exact component={Registrations} isPrivate />
	</Switch>
);

export default Routes;
