# Ayame

**Ayame** is a command-line tool for storing and retrieving book metadata,
which I call records: ID, ISBN, titles, authors, descriptions, publishing dates, ratings,
tags, and notes.

You give her a book to keep. She catalogues it and hands the record
back on request, directly from the terminal. No app, account, or sync
required; no visual gadgets and no noise. You get exactly what you ask for
and nothing else.

> Ayame is currently a learning project, being built from scratch as I learn
> and explore software development.

### A Backstory

Ayame is a librarian, and a very old one at that. Ever seeking knowledge
and understanding, and never quite satisfied with what she has found, few
books have ever escaped her grasp. She gathered and catalogued them all
within a grand library she built for herself in another dimension, where
no flame should have reached...

Unfortunately, it burned anyway.

Nothing was left of her life's work but scattered bits. She also lost most
of her powers in the process, and could no longer leave, or do much else
than wander and wonder among the remains.

After an untold amount of time had passed, quite unexpectedly, a stranger
found their way into her dimension. The stranger arrived carrying a book
and asking whether she wanted to follow them.

How they managed to find her is a question she has no answer to. Alas,
one book was not much to work with, but it was a beginning.

### Roadmap

#### 1. Building the Book class

- [ ] Nail down every field and whether it holds one value or many values. 
   1. One: id, title, isbn, pages, pubDate, description, rating
   2. Many: authors, tags, notes
- [ ] Pick the right Java collection type for the "many" fields. 
   1. authors: LinkedHashSet (order and no-dupes)
   2. tags: HashSet (no-dupes)
   3. notes: ArrayList (order, no dupes)
- [ ] Design the constructor(s).
- [ ] Write getters/setters.
- [ ] Decide on equals(), hashCode(), toString().