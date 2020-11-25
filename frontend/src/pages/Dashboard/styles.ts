import styled, { css } from 'styled-components';
import { shade } from 'polished';

export const SidebarContainer = styled.div``;

export const OutContainer = styled.div`
	display: flex;
	justify-content: flex-start;
`;

export const Container = styled.div`
	display: flex;
	margin-left: 230px;
`;

export const Content = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-left: 36px;
	margin-top: 24px;
`;

export const Filters = styled.div`
	display: flex;
	align-items: center;
`;

export const Filter = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;

	margin: 0 36px;
`;

export const FilterOptions = styled.div`
	display: flex;
	align-items: center;

	div:first-of-type {
		margin: 0;
		margin-right: 4px;
	}

	div {
		margin: 0 4px;
	}
`;

export const Pets = styled.div`
	display: flex;
	justify-content: space-around;
	flex-wrap: wrap;
	margin-top: 36px;

	> div {
		margin: 54px;
	}
`;

export const SearchBar = styled.div`
	display: flex;
	align-items: center;

	padding: 12px;
	border-radius: 12px;

	background-color: #c4c4c4;

	input {
		outline: 0;
		border: 0;
		background-color: transparent;
		margin-left: 12px;
	}
`;

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
