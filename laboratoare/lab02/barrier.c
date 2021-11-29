#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define NUM_THREADS 2

typedef struct {
	int id;
	pthread_barrier_t *barrier;
} info;

void *f(void *arg)
{
	info *thread_info = (info *)arg;

	int thread_id = thread_info->id;
	pthread_barrier_t *barrier = thread_info->barrier;

	if (thread_id == 1) {
		printf("1\n");
	}

	pthread_barrier_wait(barrier);

	if (thread_id == 0) {
		printf("2\n");
	}

	pthread_exit(NULL);
}

int main(int argc, char **argv)
{
	int i, r;
	void *status;
	pthread_t threads[NUM_THREADS];

	info *arguments = malloc(NUM_THREADS * sizeof(info));

	// initialize the barrier
	pthread_barrier_t barrier;

	r = pthread_barrier_init(&barrier, NULL, NUM_THREADS);
	if (r) {
		printf("Eroare la initializarea barierei\n");
	}

	for (i = 0; i < NUM_THREADS; i++) {
		arguments[i].id = i;
		arguments[i].barrier = &barrier;

		r = pthread_create(&threads[i], NULL, f, &arguments[i]);

		if (r) {
			printf("Eroare la crearea thread-ului %d\n", i);
			exit(-1);
		}
	}

	for (i = 0; i < NUM_THREADS; i++) {
		r = pthread_join(threads[i], &status);

		if (r) {
			printf("Eroare la asteptarea thread-ului %d\n", i);
			exit(-1);
		}
	}

	r = pthread_barrier_destroy(&barrier);
	if (r) {
		printf("Eroare la dezalocarea barierei\n");
	}

	return 0;
}
