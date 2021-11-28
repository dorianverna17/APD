#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define NUM_THREADS 2

void *f(void *arg) {
  	long id = *(long*)arg;
	for (int i = 0; i < 100; i++) 
  		printf("Hello World din thread-ul %ld din functia f!\n", id);
  	pthread_exit(NULL);
}

void *g(void *arg) {
  	long id = *(long*)arg;
	for (int i = 0; i < 100; i++) 
  		printf("Hello World din thread-ul %ld din functia g!\n", id);
  	pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
	pthread_t threads[NUM_THREADS];
  	int r;
  	long id;
  	void *status;
	long ids[NUM_THREADS];
	void *functions[NUM_THREADS];

	for (int i = 0; i < NUM_THREADS; i++) {
		if (i % 2 == 0)
			functions[i] = f;
		else
			functions[i] = g;
	}

  	for (id = 0; id < NUM_THREADS; id++) {
		ids[id] = id; 
		r = pthread_create(&threads[id], NULL, functions[id], &ids[id]);

		if (r) {
	  		printf("Eroare la crearea thread-ului %ld\n", id);
	  		exit(-1);
		}
  	}

  	for (id = 0; id < NUM_THREADS; id++) {
		r = pthread_join(threads[id], &status);

		if (r) {
	  		printf("Eroare la asteptarea thread-ului %ld\n", id);
	  		exit(-1);
		}
  	}

  	pthread_exit(NULL);
}
