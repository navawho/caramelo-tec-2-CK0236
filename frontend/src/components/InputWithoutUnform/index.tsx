import React, { InputHTMLAttributes, useState, useCallback } from 'react';
import { IconBaseProps } from 'react-icons';

import { Container } from './styles';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
	value: string;
	icon?: React.ComponentType<IconBaseProps>;
}

const Input: React.FC<InputProps> = ({ value, icon: Icon, ...rest }) => {
	const [isFocused, setIsFocused] = useState(false);
	const [isFilled, setIsFilled] = useState(false);

	const handleInputFocus = useCallback(() => {
		setIsFocused(true);
	}, []);

	const handleInputBlur = useCallback(() => {
		setIsFocused(false);

		setIsFilled(!!value);
	}, [value]);

	return (
		<Container isFilled={isFilled} isFocused={isFocused}>
			{Icon && <Icon size={20} />}
			<input onFocus={handleInputFocus} onBlur={handleInputBlur} {...rest} />
		</Container>
	);
};

export default Input;
