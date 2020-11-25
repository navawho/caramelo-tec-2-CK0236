import styled, { css } from 'styled-components';
import { lighten } from 'polished';

import Tooltip from '../Tooltip';

interface ContainerProps {
	isFocused: boolean;
	isFilled: boolean;
	isErrored: boolean;
}

interface ErrorProps {
	isErrored: boolean;
}

export const Container = styled.div<ContainerProps>`
	background: #fff;
	border-radius: 32px;
	padding: 12px 16px;
	width: 100%;
	display: flex;
	align-items: center;
	border: 1px solid ${lighten(0.3, '#333333')};
	color: ${lighten(0.3, '#333333')};

	transition: 0.3s;

	& + div {
		margin-top: 8px;
	}

	svg {
		margin-right: 16px;
	}

	${(props) =>
		props.isErrored &&
		css`
			border-color: #c53030;
		`}

	${(props) =>
		props.isFocused &&
		css`
			color: #c94217;
			border-color: #c94217;
		`}

	${(props) =>
		props.isFilled &&
		css`
			color: #c94217;
		`}

	input {
		flex: 1;
		background: transparent;
		border: 0;
		color: #333333;
		&::placeholder {
			color: ${lighten(0.4, '#333333')};
		}
	}
`;

export const Error = styled(Tooltip)<ErrorProps>`
	height: 20px;
	margin-left: 16px;
	visibility: hidden;

	${(props) =>
		props.isErrored &&
		css`
			visibility: visible;
		`}

	svg {
		margin: 0;
	}

	span {
		background: #c53030;
		color: #fff;
		&::before {
			border-color: #c53030 transparent;
		}
	}
`;
