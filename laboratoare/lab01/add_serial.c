#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/*
    schelet pentru exercitiul 5
*/

int* arr;
int array_size;

typedef struct {
    int id;
    int nr_threads;
} info;

void *f(void *arg) {
    info inf = *((info *) arg);

    int start = inf.id * array_size / inf.nr_threads;
    int end;

    if ((inf.id + 1) * array_size / inf.nr_threads > array_size)
        end = array_size;
    else
        end = (inf.id + 1) * array_size / inf.nr_threads;

    for (int i = start; i < end; i++) {
        arr[i] += 100;
    }
}

int main(int argc, char *argv[]) {
    if (argc < 2) {
        perror("Specificati dimensiunea array-ului\n");
        exit(-1);
    }

    array_size = atoi(argv[1]);

    arr = malloc(array_size * sizeof(int));
    for (int i = 0; i < array_size; i++) {
        arr[i] = i;
    }

    for (int i = 0; i < array_size; i++) {
        printf("%d", arr[i]);
        if (i != array_size - 1) {
            printf(" ");
        } else {
            printf("\n");
        }
    }

    // TODO: aceasta operatie va fi paralelizata
  	// for (int i = 0; i < array_size; i++) {
    //     arr[i] += 100;
    // }

    // get the number of cores
    long cores = sysconf(_SC_NPROCESSORS_CONF);
    int err;

    pthread_t threads[cores];
    info *args = malloc(cores * sizeof(info));

    for (int i = 0; i < cores; i++) {
        args[i].id = i;
        args[i].nr_threads = cores;

        err = pthread_create(&threads[i], NULL, f, &args[i]);
        if (err) {
            printf("Eroare la crearea thread-ului\n");
        }
    }

    for (int i = 0; i < cores; i++) {
        err = pthread_join(threads[i], NULL);

        if (err) {
            printf("Eroare la asteptarea thread-ului\n");
        }
    }

    for (int i = 0; i < array_size; i++) {
        printf("%d", arr[i]);
        if (i != array_size - 1) {
            printf(" ");
        } else {
            printf("\n");
        }
    }

  	pthread_exit(NULL);
}
