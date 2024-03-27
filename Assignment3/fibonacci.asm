	.data
n:
	10
	.text
main:
	load %x0, $n, %x1			;storing input n in x1
	beq %x1, 0, exit			;if n==0 then end the program
	addi %x0, 0, %x2			;x2 refers to the current value of the fib number init to 0
	addi %x0, 65535, %x3		;x3 refers to the current address location where the fib number is to be stored, init to 65535
	store %x2, 0, %x3			;store value x2 in location x3
	subi %x1, 1, %x1			;decrement x1/n
	subi %x3, 1, %x3			;decrement x3/memory location
	beq %x1, 0, exit			;if n==0, then end the program
	addi %x2, 1, %x2			;increasing x2 by 1, to get 2nd fib number
	store %x2, 0, %x3			;storing 2nd fib number
	subi %x1, 1, %x1			;decrement x1/n	
	subi %x3, 1, %x3			;decrement x3/memory location
	beq %x1, 0, exit			;if n==0, then end the program
loop:
	addi %x3, 1, %x4			;storing next fib number location in x4
	load %x4, 0, %x5			;storing next fib number value in x5
	addi %x3, 2, %x6			;storing the second next fib number location in x6
	load %x6, 0, %x7			;storing the second next fib number value in x7
	add %x5, %x7, %x2			;Adding x5 and x7 and storing in x2
	store %x2, 0, %x3			;stroing the current fib number
	subi %x1, 1, %x1			;decrement x1/n	
	subi %x3, 1, %x3			;decrement x3/memory location
	bgt %x1, 0, loop			;continuing the loop if n>0
	end							;end
exit:
	end							;end