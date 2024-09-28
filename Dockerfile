FROM ubuntu:latest
LABEL authors="prath"

ENTRYPOINT ["top", "-b"]