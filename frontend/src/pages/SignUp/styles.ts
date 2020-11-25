import styled from 'styled-components';
import { shade } from 'polished';

export const Container = styled.div`
	height: 100%;

	display: flex;
	align-items: stretch;
`;

export const Content = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

	width: 100%;

	img {
		width: 200px;
		height: 200px;
		cursor: pointer;
	}
`;

export const Card = styled.div`
	display: flex;
	flex-direction: column;
	margin-bottom: 200px;

	box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.25);
	border-radius: 8px;

	background-color: #fff;

	padding: 42px;

	h3 {
		font-size: 30px;
		margin-bottom: 18px;
	}
`;

export const Inputs = styled.div`
	display: flex;
	flex-direction: column;

	.password-wrapper {
		margin-top: 20px;
		margin-bottom: 8px;
	}

	a {
		color: rgba(201, 66, 23, 0.93);
		margin-top: 24px;
		text-decoration: underline;
		cursor: pointer;
		font-size: 14px;
		align-self: center;

		:hover {
			color: ${shade(0.2, 'rgba(201, 66, 23, 0.93)')};
		}
	}

	button {
		margin-top: 36px;

		border-radius: 24px;
		padding: 12px 36px;

		color: #fff;
	}
`;
