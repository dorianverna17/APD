#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define NUM_THREADS 2

int a = 0;

typedef struct {
	// int id;
	pthread_mutex_t *mutex;
} info;

void *f(void *arg)
{
	info *thread_info = (info *) arg;

	pthread_mutex_lock(thread_info->mutex);

	a += 2;

	pthread_mutex_unlock(thread_info->mutex);

	pthread_exit(NULL);
}

int main(int argc, char *argv[])
{
	int i, r;
	void *status;
	pthread_t threads[NUM_THREADS];
	// int arguments[NUM_THREADS];

	info *data_to_send = malloc(sizeof(info) * NUM_THREADS);

	// initialize the mutex
	pthread_mutex_t mutex;
	
	r = pthread_mutex_init(&mutex, NULL);
	if (r) {
		printf("Eroare la initializarea mutex-ului\n");
	}

	for (i = 0; i < NUM_THREADS; i++) {
		// data_to_send->id = i;
		data_to_send[i].mutex = &mutex;
		
		r = pthread_create(&threads[i], NULL, f, &data_to_send[i]);

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

	printf("a = %d\n", a);

	r = pthread_mutex_destroy(&mutex);
	if (r) {
		printf("Eroare la dezalocarea mutex-ului\n");
	}

	return 0;
}
