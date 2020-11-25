import styled from 'styled-components';

export const Container = styled.div`
	height: 100vh;

	display: flex;
	align-items: stretch;
`;

export const SidebarContainer = styled.div``;

export const Content = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	padding-top: 64px;

	width: 100%;

	form {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		legend {
			font-family: 'Poppins', sans-serif;
			font-weight: 700;
			font-size: 24px;
			margin-bottom: 24px;
		}

		button {
			margin-top: 24px;

			border-radius: 24px;
			padding: 12px 36px;

			color: #fff;
		}

		.phone {
			margin-bottom: 36px;
		}
	}
`;
