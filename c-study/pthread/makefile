CFLAGS+=-pthread
LDFLAGS+=-pthread
OUT_DIR=out

all:cleanup create1 primer thread_compete primer_wrong main_care_other

cleanup:cleanup.c
	gcc cleanup.c -o ${OUT_DIR}/cleanup

create1:create1.c
	gcc create1.c -o ${OUT_DIR}/create1

primer:primer.c
	gcc primer.c -o ${OUT_DIR}/primer

thread_compete:thread_compete.c
	gcc thread_compete.c -o ${OUT_DIR}/thread_compete

primer_wrong:primer_wrong.c
	gcc primer_wrong.c -o ${OUT_DIR}/primer_wrong

main_care_other:main_care_other.c
	gcc main_care_other.c -o ${OUT_DIR}/main_care_other

clean:
	rm ${OUT_DIR}/*