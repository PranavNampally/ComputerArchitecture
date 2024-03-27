	.data
a:
	10
	.text
main:
	addi %x0, 0, %x2			; Here %x2 is 0
	load %x0, $a, %x1			; We store our number in %x1
	addi %x0, 2, %x3			; We start checking our number is a multiple of any number starting with 2, so 2 is stored in %x3
	blt %x1, %x3, notprime		; If given number is less than 2 then its not prime
	subi %x1, 1, %x5			; we store a-1 in %x5 which is later used
loop:
	div %x1, %x3, %x4			; We divide our number with other numbers to check if its a multiple of it or not, we store the remainder in %x31
	beq %x31, %x0, notprime		; If Modulus is Zero then the number is a multiple of other number so its not a prime number
	beq %x3, %x5, prime			; If the other number which we are dividing our number with is equal to a-1 (stored in %x5)
	addi %x3, 1, %x3			; Increment %x3 by 1
	jmp loop					; Jump to the label loop
notprime:
	subi %x0, 1, %x10			; Store -1 in %x10 if the number is not prime
	end							; End 
prime:
	addi %x0, 1, %x10			; Store 1 in %x10 if the number is prime
	end							; End