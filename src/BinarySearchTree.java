// Wir importieren eine dynamische Liste, um Traversierungsergebnisse zu speichern.
import java.util.ArrayList;
// Wir importieren Deque, weil wir sie als Stack für die iterative In-Order-Traversierung verwenden.
import java.util.Deque;
// Wir importieren LinkedList, weil sie das Deque-Interface effizient bereitstellt.
import java.util.LinkedList;
// Wir importieren List als allgemeines Rückgabe-Interface für Ergebnislisten.
import java.util.List;

// Diese Klasse implementiert einen binären Suchbaum (BST) für int-Werte.
public class BinarySearchTree {
    // Dieses Feld zeigt auf die Wurzel des Baums; bei leerem Baum ist es null.
    private Node root;

    // Diese innere Klasse beschreibt einen einzelnen Knoten im Baum.
    private static class Node {
        // In diesem Feld speichern wir den eigentlichen Zahlenwert des Knotens.
        int value;
        // Dieses Feld zeigt auf das linke Kind (alle kleineren Werte).
        Node left;
        // Dieses Feld zeigt auf das rechte Kind (alle größeren Werte).
        Node right;

        // Dieser Konstruktor erstellt einen neuen Knoten mit einem Startwert.
        Node(int value) {
            // Hier übernehmen wir den übergebenen Wert in das Knotenfeld.
            this.value = value;
        }
    }

    // Diese Methode fügt einen Wert ohne Rekursion in den Baum ein.
    public void insert(int value) {
        // Wenn der Baum leer ist, wird der neue Wert direkt zur Wurzel.
        if (root == null) {
            // Wir erzeugen den ersten Knoten des Baums.
            root = new Node(value);
            // Danach ist das Einfügen abgeschlossen.
            return;
        }

        // current läuft beim Suchen der passenden Einfügeposition durch den Baum.
        Node current = root;

        // Die Schleife läuft so lange, bis wir den Wert eingefügt oder verworfen haben.
        while (true) {
            // Falls der neue Wert kleiner ist, müssen wir nach links gehen.
            if (value < current.value) {
                // Wenn links noch kein Knoten existiert, fügen wir dort ein.
                if (current.left == null) {
                    // Wir hängen den neuen Knoten als linkes Kind an.
                    current.left = new Node(value);
                    // Einfügen ist erledigt, daher verlassen wir die Methode.
                    return;
                }
                // Sonst gehen wir einen Schritt tiefer nach links.
                current = current.left;
            // Falls der neue Wert größer ist, müssen wir nach rechts gehen.
            } else if (value > current.value) {
                // Wenn rechts noch kein Knoten existiert, fügen wir dort ein.
                if (current.right == null) {
                    // Wir hängen den neuen Knoten als rechtes Kind an.
                    current.right = new Node(value);
                    // Einfügen ist erledigt, daher verlassen wir die Methode.
                    return;
                }
                // Sonst gehen wir einen Schritt tiefer nach rechts.
                current = current.right;
            // Dieser Fall bedeutet: der Wert existiert bereits im Baum.
            } else {
                // Duplikate ignorieren wir, damit jeder Wert nur einmal vorkommt.
                return;
            }
        }
    }

    // Diese Methode prüft ohne Rekursion, ob ein Wert im Baum vorhanden ist.
    public boolean contains(int value) {
        // Wir starten die Suche an der Wurzel.
        Node current = root;

        // Solange wir noch einen Knoten betrachten können, suchen wir weiter.
        while (current != null) {
            // Wenn der gesuchte Wert gefunden wurde, geben wir true zurück.
            if (value == current.value) {
                // Treffer: Wert ist im Baum enthalten.
                return true;
            }
            // Je nach Vergleich wechseln wir in den linken oder rechten Teilbaum.
            current = value < current.value ? current.left : current.right;
        }

        // Wenn wir null erreicht haben, gibt es den Wert im Baum nicht.
        return false;
    }

    // Diese Methode löscht einen Wert ohne Rekursion aus dem Baum.
    public void delete(int value) {
        // parent speichert den Elternknoten des aktuell untersuchten Knotens.
        Node parent = null;
        // current zeigt auf den Knoten, den wir gerade bei der Suche untersuchen.
        Node current = root;

        // Wir suchen den zu löschenden Knoten iterativ.
        while (current != null && current.value != value) {
            // Bevor wir weitergehen, merken wir uns den bisherigen Knoten als parent.
            parent = current;
            // Je nach Vergleich laufen wir links oder rechts weiter.
            current = value < current.value ? current.left : current.right;
        }

        // Wenn current null ist, wurde der Wert nicht gefunden.
        if (current == null) {
            // Dann gibt es nichts zu löschen.
            return;
        }

        // Wenn der Knoten zwei Kinder hat, behandeln wir den Spezialfall zuerst.
        if (current.left != null && current.right != null) {
            // successorParent wird der Elternknoten des Inorder-Nachfolgers.
            Node successorParent = current;
            // successor startet rechts, weil dort die größeren Werte liegen.
            Node successor = current.right;

            // Wir gehen so weit wie möglich nach links zum kleinsten rechten Wert.
            while (successor.left != null) {
                // Vor dem Abstieg merken wir den aktuellen successor als parent.
                successorParent = successor;
                // Dann gehen wir einen Schritt nach links.
                successor = successor.left;
            }

            // Wir kopieren den Nachfolgerwert in den zu löschenden Knoten.
            current.value = successor.value;
            // Danach setzen wir current auf den Nachfolger, der physisch gelöscht wird.
            current = successor;
            // Ebenso setzen wir parent passend auf dessen Elternknoten.
            parent = successorParent;
        }

        // child ist das einzige Kind von current oder null, falls current ein Blatt ist.
        Node child = current.left != null ? current.left : current.right;

        // Wenn parent null ist, löschen wir die Wurzel.
        if (parent == null) {
            // Die neue Wurzel ist dann child (oder null bei leerem Restbaum).
            root = child;
        // Wenn current linkes Kind von parent ist, hängen wir child links ein.
        } else if (parent.left == current) {
            // Der linke Verweis des Elternknotens wird aktualisiert.
            parent.left = child;
        // Sonst war current das rechte Kind von parent.
        } else {
            // Der rechte Verweis des Elternknotens wird aktualisiert.
            parent.right = child;
        }
    }

    // Diese Methode liefert alle Werte in aufsteigender Reihenfolge (iterativ).
    public List<Integer> inOrderTraversal() {
        // Wir erzeugen eine Liste für das Ergebnis.
        List<Integer> result = new ArrayList<>();
        // Wir verwenden ein Deque als Stack zur Simulation des Rekursionsstapels.
        Deque<Node> stack = new LinkedList<>();
        // current startet an der Wurzel.
        Node current = root;

        // Wir laufen, solange noch Knoten zu besuchen oder im Stack sind.
        while (current != null || !stack.isEmpty()) {
            // Wir gehen so weit wie möglich nach links und merken uns den Pfad.
            while (current != null) {
                // Der aktuelle Knoten wird auf den Stack gelegt.
                stack.push(current);
                // Dann gehen wir weiter zum linken Kind.
                current = current.left;
            }

            // Jetzt nehmen wir den zuletzt gemerkten Knoten vom Stack.
            current = stack.pop();
            // Dieser Knoten wird als nächstes in der In-Order-Reihenfolge besucht.
            result.add(current.value);
            // Danach wechseln wir in seinen rechten Teilbaum.
            current = current.right;
        }

        // Am Ende geben wir die vollständig sortierte Liste zurück.
        return result;
    }

    // Diese Main-Methode demonstriert den Baum mit einem kleinen Beispiel.
    public static void main(String[] args) {
        // Wir erzeugen eine neue, anfangs leere BST-Instanz.
        BinarySearchTree bst = new BinarySearchTree();

        // Diese Werte fügen wir nacheinander in den Baum ein.
        int[] werte = {8, 3, 10, 1, 6, 14, 4, 7, 13};
        // Die Schleife durchläuft alle Werte im Array.
        for (int wert : werte) {
            // Jeder Wert wird iterativ in die BST eingefügt.
            bst.insert(wert);
        }

        // Ausgabe der sortierten Reihenfolge per In-Order-Traversierung.
        System.out.println("In-Order (sortiert): " + bst.inOrderTraversal());
        // Testausgabe: 6 sollte im Baum enthalten sein.
        System.out.println("Enthält 6? " + bst.contains(6));
        // Testausgabe: 2 wurde nie eingefügt und sollte fehlen.
        System.out.println("Enthält 2? " + bst.contains(2));

        // Wir löschen den Wert 3 aus dem Baum.
        bst.delete(3);
        // Danach geben wir erneut die sortierte Reihenfolge aus.
        System.out.println("Nach delete(3): " + bst.inOrderTraversal());
    }
}
