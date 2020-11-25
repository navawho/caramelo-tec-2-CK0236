import styled, { css } from 'styled-components';
import { shade } from 'polished';

interface RadioProps {
	isChecked: boolean;
}

export const Radio = styled.div<RadioProps>`
	display: flex;
	align-items: center;
	justify-content: center;

	background-color: #c4c4c4;
	width: 18px;
	height: 18px;
	cursor: pointer;

	svg {
		opacity: 0;
	}

	transition: background-color 0.3s;

	:hover {
		background-color: ${shade(0.1, '#c4c4c4')};
	}

	${({ isChecked }) =>
		isChecked &&
		css`
			background-color: #12d368;
			:hover {
				background-color: ${shade(0.1, '#12d368')};
			}

			svg {
				opacity: 1;
			}
		`}
`;
