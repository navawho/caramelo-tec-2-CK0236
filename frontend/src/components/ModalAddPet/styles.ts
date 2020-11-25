import styled from 'styled-components';
import { Form as Unform } from '@unform/web';

export const Form = styled(Unform)`
	padding: 48px 40px;
	display: flex;
	flex-direction: column;
	h1 {
		font-weight: 600;
		font-size: 36px;
		line-height: 36px;
		margin-bottom: 40px;
	}
	button {
		margin-top: 48px;
		align-self: flex-end;

		border-radius: 24px;
		padding: 12px 36px;

		color: white;
	}
`;
