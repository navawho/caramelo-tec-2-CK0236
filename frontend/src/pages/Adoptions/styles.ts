import styled from 'styled-components';

export const Container = styled.div`
	display: flex;
`;

export const Content = styled.div`
	margin-left: 230px;
	width: 100%;
	justify-content: space-around;
	display: flex;
	h2 {
		margin: 20px 0;
	}
	.cardContainer {
		margin: 20px 0;
	}

	.cardContainer > div {
		margin-bottom: 30px;
	}
`;

export const LeftContent = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
`;

export const RightContent = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
`;
