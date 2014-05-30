import 'dart:html';

main () {
	print('Hello dart!');
	print(new DateTime.now().toString());
	querySelector('#field').text =  new DateTime.now().toString();
	int i = 0;
	do {
		i++;
		String a = "* $i";
		a = a + new DateTime.now().toString();
		print(a);
//		print(new DateTime.now().toString());
		querySelector('#field$i').text = a;
	} while (i < 3);
}
