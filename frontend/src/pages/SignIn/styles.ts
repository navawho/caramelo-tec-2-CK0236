import styled from 'styled-components';
import { shade } from 'polished';

export const Container = styled.div`
	height: 100%;
	display: flex;
	align-items: center;
`;

export const Content = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: flex-start;
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

	label:last-of-type {
		margin-top: 18px;
	}

	label {
		font-size: 24px;
		margin-bottom: 12px;
	}

	a {
		color: rgba(201, 66, 23, 0.93);
		margin-top: 24px;
		text-decoration: underline;
		cursor: pointer;
		font-size: 14px;
		align-self: center;

		:hover {
			color: ${shade(0.2, 'rgba(201, 66, 23, 0.93)')}
		}
	}

	button {
		margin-top: 24px;

		border-radius: 24px;
		padding: 12px 36px;

		color: #fff;
	}
`;
