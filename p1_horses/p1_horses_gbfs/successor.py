from horse import Horse
from node  import Node
import eval

def successor(father, m, n, goal):
    childes = []
    for h in father.horses:
        for i in [-1, 1]:
            'first move'
            '0:top 2:bottom'
            for j in [-1, 1]:
                for t in [1, 2]:
                    y_c = i
                    x_c = j
                    if t == 1:
                        y_c *= 2
                    else:
                        x_c *= 2
                    new_horse = Horse(h.x + x_c, h.y + y_c)
                    if check_for_bound(new_horse, m, n):
                        new_child = Node(None, None)
                        new_child.horses = father.horses.copy()
                        new_child.horses.remove(h)
                        if check_for_treat(new_horse, new_child.horses):
                            new_child.horses.append(new_horse)
                            new_child.parent = father
                            if new_child not in childes:
                                    childes.append(new_child)
                                    new_child.calc_score()
                                    new_child.cost = eval.evaluation(new_child, goal)
                                    
    return childes


def check_for_bound(horse, m, n):
    ret = m > horse.x >= 0 <= horse.y < n
    return ret


def check_for_treat(horse, horses):
    for i in [-1, 1]:
        for j in [-1, 1]:
            for t in [1, 2]:
                y_c = i
                x_c = j
                if t == 1:
                    y_c *= 2
                else:
                    x_c *= 2
                new_horse = Horse(horse.x + x_c, horse.y + y_c)
                if new_horse in horses:
                    return False
    return True
