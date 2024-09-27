CFLAGS+=-pthread
LDFLAGS+=-pthread
OUT_DIR=out

all:struct_over

struct_over:struct_over.cpp
	g++ struct_over.cpp -o ${OUT_DIR}/struct_over

clean:
	rm ${OUT_DIR}/*