import React, { useState } from 'react';
import { FiCheck } from 'react-icons/fi';

import { Radio } from './styles';

interface Props {
	handleClick: (newState: string) => void;
	label: string;
}

const Checkbox: React.FC<Props> = ({ handleClick, label }) => {
	const [isChecked, setIsChecked] = useState(false);

	return (
		<Radio
			isChecked={isChecked}
			onClick={() => {
				setIsChecked(!isChecked);
				handleClick(label);
			}}
		>
			<FiCheck color="#fff" />
		</Radio>
	);
};

export default Checkbox;
