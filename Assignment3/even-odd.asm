	.data
a:
	10
	.text
main:
	load %x0, $a, %x1 			;storing input a in x0
	andi %x1, 1, %x3			;Bitwise and operation with 1
	beq %x3, %x0, even			;If result is 0, then the number is even, so branch to even label
	addi %x0, 1, %x10			;this line is executed for odd, hence store 1 in x10
	end
even:
	subi %x0, 1, %x10			;This is executed if number is event so store -1 in x10
	end