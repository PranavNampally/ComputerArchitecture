	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	addi %x0, 0, %x3			; Main label is for initialization. We store 0 in %x3 (iterator i)
	load %x0, $n, %x6			; We store n in %x6
	subi %x6, 1, %x6			; We decrement %x6 by 1 which is used later 
loopi:
	addi %x0, 0, %x4			; We set %x4 (j iterator) to 0
	blt %x3, %x6, tp			; if i<n-1 go to tp where the condition for inner loop is checked
	end							; End
tp:
	addi %x6, 0, %x9			; We store n-1 in %x9
	sub %x9, %x3, %x9			; Now %x9 has n-i-1
	blt %x4, %x9, loopj			; If j<n-i-1 then the inner loop runs
	addi %x3, 1, %x3			; Increment i by 1 if inner loop is completed
	jmp loopi					; Jump to outer loop
loopj:
	load %x4, $a, %x5			; %x5 = a[j]
	addi %x4, 1, %x8			; %x8 = %x4 +1
	load %x8, $a, %x7			; %x7 = a[j+1]
	blt %x5, %x7, swap			; If a[j]<a[j+1] then swap them
increj:
	addi %x4, 1, %x4			; Increment j by 1
	jmp tp						; Jump to tp
swap:
	addi %x5, 0, %x11			; %x11 = %x5
	addi %x7, 0, %x5			; %x5 = %x7
	addi %x11, 0, %x7			; %x7 = %x11
	store %x7, $a, %x8			; a[x8] = %x7
	store %x5, $a, %x4			; a[x4] = %x5
	jmp increj					; Jump to increj