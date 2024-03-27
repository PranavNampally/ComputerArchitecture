	.data
a:
	10
	.text
main:
	load %x0, $a, %x1 				;storing input a in x1
	addi %x1, 0, %x3				;making a copy of x2 and storing in x3
	addi %x0, 0, %x2				;initializing the reverse number in x2
loop:
	divi %x1, 10, %x1				;dividing n by 10
	add %x2, %x31, %x2				;adding the remainder to x2 
	beq %x1, %x0, check				;if n==0, then break and go to check label
	muli %x2, 10, %x2				;multuplying x2 with 10, so that we can get reverse of n in x2
	jmp loop						;unconditional branch to starting of loop
check:
	beq %x3, %x2, equal				;if number(x3) and its reverse(x2) are equal then number is palindrome so branch to equal
	subi %x0, 1, %x10				;else, number is not palindrome store -1 in x10
	end								;end	
equal:
	addi %x0, 1, %x10				;if number is palindrome store 1 in x10
	end