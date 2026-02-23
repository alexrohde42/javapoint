// Diese Klasse beschreibt eine einfach verkettete Liste für beliebige Datentypen T.
public class SinglyLinkedList<T> {

    // Diese innere Klasse steht für einen einzelnen Knoten der Liste.
    private static class Node<T> {
        // Hier speichern wir den eigentlichen Datenwert des Knotens.
        private T data;

        // Dieser Verweis zeigt auf den nächsten Knoten in der Kette.
        private Node<T> next;

        // Der Konstruktor erstellt einen Knoten mit dem übergebenen Datenwert.
        public Node(T data) {
            // Wir übernehmen den übergebenen Wert in das Datenfeld.
            this.data = data;
        }
    }

    // head zeigt auf den ersten Knoten der Liste (oder null bei leerer Liste).
    private Node<T> head = null;
    // size zählt, wie viele Elemente aktuell in der Liste gespeichert sind.
    int size;

    // Diese Methode hängt ein neues Element ans Ende der Liste an.
    public void add(T data) {
        // Wenn die Liste noch leer ist, wird der neue Knoten der Kopf.
        if (head == null) {
            // Wir erstellen den ersten Knoten der Liste.
            head = new Node<>(data);
        } else {
            // Sonst laufen wir vom Kopf aus bis zum letzten Knoten.
            Node<T> current = head;
            // Solange ein nächster Knoten existiert, gehen wir weiter.
            while (current.next != null) {
                // Ein Schritt vorwärts in der Liste.
                current = current.next;
            }
            // Hinter dem letzten Knoten wird der neue Knoten angehängt.
            current.next = new Node<>(data);
        }
        // Nach dem Einfügen erhöhen wir die Größe um 1.
        size++;
    }

    // Diese Methode fügt ein Element an einer gewünschten Position ein.
    public void insert(int index, T data) {
        // Wir prüfen, ob der Index zum Einfügen gültig ist.
        if (index < 0 || index > size) {
            // Bei ungültigem Index werfen wir eine Ausnahme.
            throw new IndexOutOfBoundsException();
        }

        // Wir erzeugen den neuen Knoten, der eingefügt werden soll.
        Node<T> newNode = new Node<>(data);

        // Sonderfall: Einfügen am Listenanfang.
        if (index == 0) {
            // Der neue Knoten zeigt auf den bisherigen Kopf.
            newNode.next = head;
            // Der neue Knoten wird zum neuen Kopf.
            head = newNode;
        } else {
            // Wir holen den Knoten direkt vor der Einfügeposition.
            Node<T> prev = getNode(index - 1);
            // Der neue Knoten zeigt auf den bisherigen Nachfolger.
            newNode.next = prev.next;
            // Der Vorgängerknoten zeigt jetzt auf den neuen Knoten.
            prev.next = newNode;
        }

        // Nach dem Einfügen erhöhen wir die Größe um 1.
        size++;
    }

    // Diese Methode ersetzt den Datenwert an einer bestimmten Position.
    public void set(int index, T data) {
        // Wir holen den Knoten am Index und überschreiben dessen Daten.
        getNode(index).data = data;
    }

    // Diese Methode liefert den Datenwert an einer bestimmten Position zurück.
    public T get(int index) {
        // Wir holen den Knoten und geben seinen Datenwert zurück.
        return getNode(index).data;
    }

    // Diese Methode entfernt das Element an einer bestimmten Position.
    public boolean remove(int index) {
        // Wir prüfen, ob der Index für vorhandene Elemente gültig ist.
        if (index < 0 || index >= size) {
            // Bei ungültigem Index werfen wir eine Ausnahme.
            throw new IndexOutOfBoundsException();
        }

        // Sonderfall: Das erste Element wird entfernt.
        if (index == 0) {
            // Der Kopf rückt auf das zweite Element vor.
            head = head.next;
        } else {
            // Wir holen den Knoten vor dem zu entfernenden Element.
            Node<T> prev = getNode(index - 1);
            // Wir überspringen den Zielknoten und verknüpfen direkt weiter.
            prev.next = prev.next.next;
        }
        // Nach dem Entfernen verringern wir die Größe um 1.
        size--;
        // true signalisiert: Entfernen war erfolgreich.
        return true;
    }

    // Diese Methode entfernt das erste Vorkommen eines Datenwerts.
    public boolean remove(T data) {
        // Wir bestimmen zuerst den Index des gesuchten Wertes.
        int index = indexOf(data);
        // Wenn der Wert nicht existiert, geben wir false zurück.
        if (index == -1) {
            // Nichts entfernt, da kein Treffer vorhanden ist.
            return false;
        }
        // Bei Treffer entfernen wir über die Index-Variante.
        remove(index);
        // true signalisiert: Element wurde gefunden und entfernt.
        return true;
    }

    // Diese Methode liefert den Index des ersten Vorkommens eines Wertes.
    public int indexOf(T data) {
        // current startet am Kopf der Liste.
        Node<T> current = head;
        // Wir laufen alle gültigen Indizes der Liste durch.
        for (int i = 0; i < size; i++) {
            // Wir vergleichen null-sicher auf inhaltliche Gleichheit.
            boolean equals = (data == null && current.data == null)
                    || (data != null && data.equals(current.data));
            // Wenn Gleichheit vorliegt, geben wir den gefundenen Index zurück.
            if (equals) {
                return i;
            }
            // Sonst gehen wir einen Schritt weiter.
            current = current.next;
        }
        // Falls kein Treffer gefunden wurde, geben wir -1 zurück.
        return -1;
    }

    // Diese Hilfsmethode liefert den Knoten an einem gültigen Index.
    private Node<T> getNode(int index) {
        // Wir prüfen den gültigen Bereich für vorhandene Elemente.
        if (index < 0 || index >= size) {
            // Bei ungültigem Index wird eine Ausnahme geworfen.
            throw new IndexOutOfBoundsException();
        }

        // Wir starten am Listenanfang.
        Node<T> current = head;

        // Wir laufen genau so viele Schritte, bis der Zielindex erreicht ist.
        for (int i = 0; i < index; i++) {
            // Ein Schritt weiter zum nächsten Knoten.
            current = current.next;
        }

        // Der gefundene Knoten wird zurückgegeben.
        return current;
    }
}
