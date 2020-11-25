import styled from 'styled-components';
import { shade } from 'polished';

export const Container = styled.div`
	display: flex;
	justify-content: center;
	position: fixed;
	width: 230px;
	height: 100vh;
	background-color: #ffffff;
	box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.25);
`;

export const NavContainer = styled.nav`
	display: flex;
	margin-top: 20px;
	flex-direction: column;
	justify-content: space-between;
	.topNav,
	.botNav {
		display: flex;
		flex-direction: column;

		.image {
			align-self: center;
		}
	}
`;

export const NavItem = styled.div`
	margin: 10px 0;
	display: flex;
	cursor: pointer;
	align-items: center;
	font-family: 'Poppins';
	font-size: 18px;
	svg {
		margin: 10px;
	}
	a {
		display: flex;
		align-items: center;
		text-decoration: none;
		color: inherit;
	}
	transition: 0.3s;
	:hover {
		color: ${shade(1, '#333333')};
	}
	:active {
		color: #c94217;
	}

	img {
		width: 100px;
		height: 100px;
	}
`;
