def evaluation(node, goal):
    return hirustic(node, goal)


def hirustic(node, goal):
    h = 0
    for horse in node.horses:
        cost = 1000
        for goal_horse in goal.horses:
            newCost = abs(horse.x - goal_horse.x) + abs(horse.y - goal_horse.y)
            if newCost < cost:
                cost = newCost
        h += cost / 3

    return h
