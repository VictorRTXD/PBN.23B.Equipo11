#include <stdio.h>

int main() {
    int numero;
    int a = 0, b = 0, c = 0;

    // esto es el index
    scanf("%d", &numero);
    switch (numero)
    {
    case 1:
        scanf("%d", &a); //a
        scanf("%d", &b);//b
        c = a + b;
        printf("%d", c);
        break;
    case 2:
        scanf("%d", &a); //a
        scanf("%d", &b);//b
        c = a - b;
        printf("%d", c);
        break;
    case 3:
        scanf("%d", &a); //a
        scanf("%d", &b);//b
        c = a * b;
        printf(" %d", c);
        break;
    case 4:
        scanf("%d", &a); //a
        scanf("%d", &b);//b
        c = a / b;
        printf(" %d", c);
        break;
    case 5:
        printf("hola mundo");
        break;
    
    
    default:
        printf("adios mundo cruel");
    }

    return 0;
}