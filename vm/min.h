#include <stdlib.h>

/* parsing */
void min_parse(char *code);

/* lemon stuff */
void *MinParserAlloc(void *(*)(size_t));
void MinParser(void *, int, void*);
void MinParserFree(void *, void (*)(void*));
