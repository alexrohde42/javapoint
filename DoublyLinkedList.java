// Wir importieren List, damit die Vorgabe aus der Aufgabenstellung enthalten ist.
import java.util.List;

// Diese Klasse beschreibt eine doppelt verkettete Liste für beliebige Datentypen T.
public class DoublyLinkedList<T> {
    // Diese innere Klasse stellt einen einzelnen Knoten der Liste dar.
    private static class Node<T> {
        // Hier speichern wir den eigentlichen Inhalt des Knotens.
        private T data;

        // Dieser Verweis zeigt auf den nächsten Knoten in der Liste.
        private Node<T> next;
        // Dieser Verweis zeigt auf den vorherigen Knoten in der Liste.
        private Node<T> prev;

        // Der Konstruktor erstellt einen Knoten mit einem übergebenen Datenwert.
        public Node(T data) {
            // Wir übernehmen den übergebenen Wert in das Datenfeld.
            this.data = data;
        }
    }

    // head zeigt auf den ersten Knoten der Liste; bei leerer Liste ist head null.
    private Node<T> head = null;

    // size speichert die aktuelle Anzahl der Elemente in der Liste.
    private int size = 0;

    // Diese Methode hängt ein neues Element ans Ende der Liste an.
    public void add(T data) {
        // Wir erzeugen einen neuen Knoten mit dem übergebenen Inhalt.
        Node<T> newNode = new Node<>(data);

        // Falls die Liste noch leer ist, wird der neue Knoten direkt zum Kopf.
        if (head == null) {
            // Der erste Knoten ist gleichzeitig der Listenanfang.
            head = newNode;
            // Die Listenlänge steigt auf 1.
            size++;
            // Danach ist die Methode beendet.
            return;
        }

        // current startet am Listenanfang und läuft bis zum letzten Knoten.
        Node<T> current = head;
        // Solange es einen nächsten Knoten gibt, gehen wir weiter nach vorne.
        while (current.next != null) {
            // Wir wechseln zum nächsten Knoten.
            current = current.next;
        }

        // Der bisher letzte Knoten zeigt jetzt auf den neuen Knoten.
        current.next = newNode;
        // Der neue Knoten zeigt rückwärts auf den bisherigen letzten Knoten.
        newNode.prev = current;
        // Die Listenlänge wird um 1 erhöht.
        size++;
    }

    // Diese Methode fügt ein Element an einer bestimmten Position ein.
    public void insert(int index, T data) {
        // Wir prüfen, ob der Index im erlaubten Bereich zum Einfügen liegt.
        if (index < 0 || index > size) {
            // Bei ungültigem Index werfen wir eine verständliche Ausnahme.
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        // Wir erzeugen den neuen Knoten, der eingefügt werden soll.
        Node<T> newNode = new Node<>(data);

        // Sonderfall: Einfügen am Anfang der Liste.
        if (index == 0) {
            // Der neue Knoten zeigt auf den bisherigen Kopf.
            newNode.next = head;
            // Falls die Liste nicht leer war, muss der alte Kopf zurück zeigen.
            if (head != null) {
                // Der alte Kopf bekommt den neuen Vorgänger.
                head.prev = newNode;
            }
            // Der Listenkopf wird auf den neuen Knoten gesetzt.
            head = newNode;
            // Die Listenlänge steigt um 1.
            size++;
            // Methode ist abgeschlossen.
            return;
        }

        // Wir laufen bis zum Knoten direkt vor der Einfügeposition.
        Node<T> previous = getNode(index - 1);
        // Wir merken uns den Knoten, der bisher hinter previous steht.
        Node<T> nextNode = previous.next;

        // Wir hängen den neuen Knoten hinter previous ein.
        previous.next = newNode;
        // Der neue Knoten zeigt rückwärts auf previous.
        newNode.prev = previous;
        // Der neue Knoten zeigt vorwärts auf den alten Nachfolger.
        newNode.next = nextNode;

        // Falls es einen alten Nachfolger gab, muss dieser zurück auf newNode zeigen.
        if (nextNode != null) {
            // Rückwärtsverweis des Nachfolgers wird korrigiert.
            nextNode.prev = newNode;
        }

        // Die Listenlänge steigt um 1.
        size++;
    }

    // Diese Methode überschreibt das Element an einer bestimmten Position.
    public void set(int index, T data) {
        // Wir holen den Knoten an der gewünschten Position.
        Node<T> node = getNode(index);
        // Wir ersetzen nur den Dateninhalt, nicht die Struktur der Liste.
        node.data = data;
    }

    // Diese Methode liefert den Datenwert an einer bestimmten Position zurück.
    public T get(int index) {
        // Wir holen den Knoten an der gewünschten Position.
        Node<T> node = getNode(index);
        // Wir geben den gespeicherten Datenwert zurück.
        return node.data;
    }

    // Diese Methode entfernt das Element an einer bestimmten Position.
    public boolean remove(int index) {
        // Wir holen den zu löschenden Knoten (wirft Ausnahme bei ungültigem Index).
        Node<T> target = getNode(index);

        // prevNode ist der Knoten vor dem zu entfernenden Knoten.
        Node<T> prevNode = target.prev;
        // nextNode ist der Knoten nach dem zu entfernenden Knoten.
        Node<T> nextNode = target.next;

        // Wenn es einen Vorgänger gibt, muss dessen next angepasst werden.
        if (prevNode != null) {
            // Vorgänger zeigt nun auf den Nachfolger von target.
            prevNode.next = nextNode;
        } else {
            // Sonst wurde der Kopf gelöscht, daher rückt nextNode nach vorne.
            head = nextNode;
        }

        // Wenn es einen Nachfolger gibt, muss dessen prev angepasst werden.
        if (nextNode != null) {
            // Nachfolger zeigt nun rückwärts auf den Vorgänger von target.
            nextNode.prev = prevNode;
        }

        // Wir trennen target vollständig ab, um klare Struktur zu behalten.
        target.next = null;
        // Auch der Rückwärtsverweis wird entfernt.
        target.prev = null;

        // Die Listenlänge sinkt um 1.
        size--;
        // true signalisiert: Entfernen war erfolgreich.
        return true;
    }

    // Diese Methode entfernt das erste Vorkommen eines Datenwerts.
    public boolean remove(T data) {
        // Wir starten die Suche am Listenanfang.
        Node<T> current = head;
        // position zählt mit, an welchem Index wir gerade sind.
        int position = 0;

        // Wir laufen durch die Liste, bis wir das Ende erreichen.
        while (current != null) {
            // Wir prüfen auf Gleichheit und behandeln null-Werte sicher.
            boolean equals = (data == null && current.data == null)
                    || (data != null && data.equals(current.data));

            // Wenn wir den Wert gefunden haben, entfernen wir über den Index.
            if (equals) {
                // Der Aufruf von remove(position) übernimmt das eigentliche Umhängen.
                return remove(position);
            }

            // Wir gehen zum nächsten Knoten weiter.
            current = current.next;
            // Der Indexzähler wird entsprechend erhöht.
            position++;
        }

        // Wenn nichts gefunden wurde, geben wir false zurück.
        return false;
    }

    // Diese Methode sucht den Index des ersten Vorkommens eines Datenwerts.
    public int index(T data) {
        // Wir starten mit -1 als Standardwert für "nicht gefunden".
        int indexOfData = -1;
        // current läuft vom Kopf aus durch die Liste.
        Node<T> current = head;
        // position zählt den aktuellen Listenindex.
        int position = 0;

        // Wir durchsuchen alle Knoten bis zum Listenende.
        while (current != null) {
            // Wir prüfen wieder null-sicher auf inhaltliche Gleichheit.
            boolean equals = (data == null && current.data == null)
                    || (data != null && data.equals(current.data));

            // Wenn ein Treffer vorliegt, speichern wir den Index und brechen ab.
            if (equals) {
                // Wir übernehmen die gefundene Position.
                indexOfData = position;
                // Erstes Vorkommen reicht, daher Schleife beenden.
                break;
            }

            // Wir gehen zum nächsten Knoten.
            current = current.next;
            // Der Positionszähler steigt um 1.
            position++;
        }

        // Wir geben entweder den gefundenen Index oder -1 zurück.
        return indexOfData;
    }

    // Diese Hilfsmethode gibt den Knoten an einem gültigen Index zurück.
    private Node<T> getNode(int index) {
        // Wir prüfen den gültigen Bereich für vorhandene Elemente.
        if (index < 0 || index >= size) {
            // Bei falschem Index wird eine Ausnahme ausgelöst.
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        // current startet am Listenkopf.
        Node<T> current = head;
        // i zählt, wie viele Schritte wir schon gelaufen sind.
        int i = 0;

        // Wir laufen genau bis zum gewünschten Index.
        while (i < index) {
            // Ein Schritt nach vorne in der Liste.
            current = current.next;
            // Der Schrittzähler wird erhöht.
            i++;
        }

        // Der gefundene Knoten wird zurückgegeben.
        return current;
    }

    // Diese Methode gibt die aktuelle Länge der Liste zurück.
    public int size() {
        // Rückgabe des internen Zählers.
        return size;
    }

    // Diese Methode erstellt eine normale Java-Liste als Schnappschuss der Inhalte.
    public List<T> toList() {
        // Wir erzeugen eine neue ArrayList für die Ausgabe.
        List<T> snapshot = new java.util.ArrayList<>();
        // Wir starten am Kopfknoten.
        Node<T> current = head;

        // Wir laufen über alle Knoten und sammeln ihre Daten ein.
        while (current != null) {
            // Daten des aktuellen Knotens werden zur Ergebnisliste hinzugefügt.
            snapshot.add(current.data);
            // Danach gehen wir zum nächsten Knoten.
            current = current.next;
        }

        // Die fertige Liste wird zurückgegeben.
        return snapshot;
    }
}
