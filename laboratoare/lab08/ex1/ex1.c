#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main (int argc, char *argv[])
{
    int  numtasks, rank;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);
    MPI_Comm_rank(MPI_COMM_WORLD,&rank);

    int recv_num;
    int num;

    MPI_Status status;

    // First process starts the circle.
    if (rank == 0) {
        // First process starts the circle.
        printf("Process 0 ");

        // Generate a random number.
        srand(time(NULL));
        num = rand() % 10;

        printf("generated number is: %d\n", num);

        // Send the number to the next process.
        MPI_Send(&num, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);

        MPI_Recv(&num, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);
        
        printf("New number is %d\n", num);

    } else if (rank == numtasks - 1) {
        // Last process close the circle.
        printf("Process %d ", rank);

        // Receives the number from the previous process.
        MPI_Recv(&num, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);

        printf("receives the number: %d\n", num);

        // Increments the number.
        num++;

        // Sends the number to the first process.
        MPI_Send(&num, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    } else {
        // Middle process.
        printf("Process %d ", rank);

        // Receives the number from the previous process.
        MPI_Recv(&num, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);

        printf("receives the number: %d\n", num);

        // Increments the number.
        num++;

        // Sends the number to the next process.
        MPI_Send(&num, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
    }

    MPI_Finalize();

}

