Generate slides out of a tree of topics

To create an über jar:
----------------------

As described here:

    http://stackoverflow.com/questions/1832853/is-it-possible-to-create-an-uber-jar-containing-the-project-classes-and-the-pr

Run:

    mvn assembly:assembly -DdescriptorId=jar-with-dependencies

