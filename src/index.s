	.file	"index.c"
	.intel_syntax noprefix
	.def	___main;	.scl	2;	.type	32;	.endef
	.section .rdata,"dr"
LC0:
	.ascii "%d\0"
LC1:
	.ascii " %d\0"
LC2:
	.ascii "hola mundo\0"
LC3:
	.ascii "adios mundo cruel\0"
	.text
	.globl	_main
	.def	_main;	.scl	2;	.type	32;	.endef
_main: ;Aqui se inicia el "main"
LFB10:;Este bloque corresponde al inicio del switch
	.cfi_startproc
	push	ebp ; Operación de Transferencia de datos.
	.cfi_def_cfa_offset 8
	.cfi_offset 5, -8
	mov	ebp, esp ; Operación de Transferencia de datos.
	.cfi_def_cfa_register 5
	and	esp, -16 ; Operación Lógica
	sub	esp, 32 ; Operación Aritmética
	call	___main
	mov	DWORD PTR [esp+20], 0 ; Operación de Transferencia de datos
	mov	DWORD PTR [esp+16], 0 ; Operación de Transferencia de datos
	mov	DWORD PTR [esp+28], 0 ; Operación de Transferencia de datos
	lea	eax, [esp+24] ; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax ; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0 ; Operación de Transferencia de datos
	call	_scanf
	mov	eax, DWORD PTR [esp+24] ; Operación de Transferencia de datos
	cmp	eax, 5
	ja	L2 ; Operación de Control de flujo
	mov	eax, DWORD PTR L4[0+eax*4] ; Operación de Transferencia de datos
	jmp	eax ; Operación de Control de flujo
	.section .rdata,"dr"
	.align 4
L4: ;Este bloque podría dar los casos dentro del switch
	.long	L2
	.long	L3
	.long	L5
	.long	L6
	.long	L7
	.long	L8
	.text
L3: ;Este bloque corresponde al "CASE 1 en nuestro Switch"
	lea	eax, [esp+20] ; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax ; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0 ; Operación de Transferencia de datos
	call	_scanf
	lea	eax, [esp+16] ; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax ; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0 ; Operación de Transferencia de datos
	call	_scanf
	mov	edx, DWORD PTR [esp+20] ; Operación de Transferencia de datos
	mov	eax, DWORD PTR [esp+16] ; Operación de Transferencia de datos
	add	eax, edx ; Operación Aritmética
	mov	DWORD PTR [esp+28], eax ; Operación de Transferencia de datos
	mov	eax, DWORD PTR [esp+28] ; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax ; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0; Operación de Transferencia de datos
	call	_printf
	jmp	L9; Operación de Control de Flujo
L5:;Este bloque corresponde al "CASE 2 en nuestro Switch"
	lea	eax, [esp+20] ; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax ; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0 ; Operación de Transferencia de datos
	call	_scanf
	lea	eax, [esp+16] ; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0; Operación de Transferencia de datos
	call	_scanf
	mov	edx, DWORD PTR [esp+20]; Operación de Transferencia de datos
	mov	eax, DWORD PTR [esp+16]; Operación de Transferencia de datos
	sub	edx, eax; Operación Aritmética
	mov	eax, edx; Operación de Transferencia de datos
	mov	DWORD PTR [esp+28], eax; Operación de Transferencia de datos
	mov	eax, DWORD PTR [esp+28]; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0; Operación de Transferencia de datos
	call	_printf
	jmp	L9 ; Operación de Control de flujo
L6:;Este bloque corresponde al "CASE 3 en nuestro Switch"
	lea	eax, [esp+20]; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0; Operación de Transferencia de datos
	call	_scanf
	lea	eax, [esp+16]; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0; Operación de Transferencia de datos
	call	_scanf
	mov	edx, DWORD PTR [esp+20]; Operación de Transferencia de datos
	mov	eax, DWORD PTR [esp+16]; Operación de Transferencia de datos
	imul	eax, edx; Operación Aritmética
	mov	DWORD PTR [esp+28], eax; Operación de Transferencia de datos
	mov	eax, DWORD PTR [esp+28]; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC1; Operación de Transferencia de datos
	call	_printf
	jmp	L9; Operación de Control de flujo
L7:;Este bloque corresponde al "CASE 4 en nuestro Switch"
	lea	eax, [esp+20]; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0; Operación de Transferencia de datos
	call	_scanf
	lea	eax, [esp+16]; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC0; Operación de Transferencia de datos
	call	_scanf
	mov	eax, DWORD PTR [esp+20]; Operación de Transferencia de datos
	mov	ecx, DWORD PTR [esp+16]; Operación de Transferencia de datos
	cdq
	idiv	ecx ; Operación Artmética
	mov	DWORD PTR [esp+28], eax; Operación de Transferencia de datos
	mov	eax, DWORD PTR [esp+28]; Operación de Transferencia de datos
	mov	DWORD PTR [esp+4], eax; Operación de Transferencia de datos
	mov	DWORD PTR [esp], OFFSET FLAT:LC1; Operación de Transferencia de datos
	call	_printf
	jmp	L9 ; Operación de Control de flujo
L8:;Este bloque corresponde al "CASE 5 en nuestro Switch"
	mov	DWORD PTR [esp], OFFSET FLAT:LC2 ; Operación de Transferencia de datos
	call	_printf
	jmp	L9; Operación de control de flujo
L2:;Este bloque corresponde al "DEFAULT del Switch"
	mov	DWORD PTR [esp], OFFSET FLAT:LC3; Operación de Transferencia de datos
	call	_printf
L9:;Este bloque es el que termina el switch y pone el "return 0"
	mov	eax, 0; Operación de Transferencia de datos
	leave
	.cfi_restore 5
	.cfi_def_cfa 4, 4
	ret
	.cfi_endproc
LFE10:;En este bloque se da por terminado el main
	.ident	"GCC: (MinGW.org GCC-6.3.0-1) 6.3.0"
	.def	_scanf;	.scl	2;	.type	32;	.endef
	.def	_printf;	.scl	2;	.type	32;	.endef
