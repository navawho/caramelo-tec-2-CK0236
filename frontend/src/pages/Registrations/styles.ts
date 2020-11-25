import styled from 'styled-components';

export const Container = styled.div`
	display: flex;
`;

export const SidebarContainer = styled.div`
	display: flex;
`;

export const Content = styled.div`
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	margin-left: 230px;
	margin-top: 24px;
	min-width: 310px;

	> button {
		margin-top: 24px;

		border-radius: 24px;
		padding: 12px 36px;

		color: #fff;

		align-self: center;

		margin-bottom: 36px;
	}
`;

export const Pets = styled.div`
	margin: 10px 30px;
	display: flex;
	flex-direction: column;
`;

export const PetWrapper = styled.div`
	display: flex;

	margin-bottom: 24px;
`;

export const Solicitations = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: flex-start;
	margin-left: 24px;
	max-height: 325px;
	flex-wrap: wrap;
`;

export const Solicitation = styled.div`
	box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
	border-radius: 12px;
	background-color: #fff;
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 12px;
	p {
		font-size: 18px;
	}
	span {
		display: flex;
	}
	margin-bottom: 12px;
	margin-right: 12px;
	:last-child {
		margin-bottom: 12px;
	}
`;

export const ButtonWrapper = styled.div`
	display: flex;

	button {
		margin-top: 12px;

		border-radius: 24px;
		padding: 6px 9px;

		color: #fff;

		margin-right: 12px;

		:last-child {
			margin-right: 0px;
		}
	}
`;
