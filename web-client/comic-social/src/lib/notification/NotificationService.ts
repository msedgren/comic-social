import type {Writable} from 'svelte/store';
import {writable} from 'svelte/store';

export enum NoteType {
    Error,
    Success,
}

export interface Note {
    notificationType: NoteType,
    message: string,
}

export class NotificationService {

    #notes: Writable<ReadonlyArray<Note>> = writable([]);

    constructor() {
    }

    clear(): void {
        this.#notes.set([]);
    }

    subscribe(fn: (g: ReadonlyArray<Note>) => void): () => void {
        return this.#notes.subscribe(fn);
    }

    add(note: Note): void {
        this.#notes.update(notes => this.filterNote(note, notes).concat(note));
    }

    remove(note: Note): void {
        this.#notes.update(notes => this.filterNote(note, notes));
    }

    private filterNote(note: Note, notes: ReadonlyArray<Note>): ReadonlyArray<Note> {
        return notes.filter(current => current.message !== note.message
            || current.notificationType !== note.notificationType);
    }

}


const service = new NotificationService();

export { service as notificationService };
