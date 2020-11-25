import { formatDistanceToNow } from 'date-fns';
import ptBr from 'date-fns/locale/pt-BR';

export default function (date: string): string {
	const formatedDate = new Date(date);

	const interval = formatDistanceToNow(formatedDate, {
		addSuffix: true,
		locale: ptBr,
	});

	return interval;
}