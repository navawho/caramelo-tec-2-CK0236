import styled from 'styled-components';

export const Container = styled.div`
	background-color: #fff;
	box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.25);
	border-radius: 12px;
	padding: 12px;

	display: flex;
	flex-direction: column;

	label {
		font-weight: 500;
		margin: 6px 0;
	}

	.box-1 {
		display: flex;
		align-items: center;
		margin-bottom: 12px;

		div {
			max-width: 120px;
			word-wrap: break-word;
		}

		img {
			border-radius: 12px;
			width: 180px;
			height: 130px;
			object-fit: fill;
			margin-right: 12px;
		}

		span {
			color: #5c5c5c;
		}
	}

	p {
		max-width: 300px;
		color: #5c5c5c;
	}

	.button-wrapper {
		display: flex;
		justify-content: space-around;

		button {
			margin-top: 12px;

			width: 130px;

			border-radius: 24px;
			padding: 12px 0px;

			color: #fff;

			align-self: center;
		}
	}
`;
