FROM ubuntu:latest
LABEL authors="Titan"

ENTRYPOINT ["top", "-b"]