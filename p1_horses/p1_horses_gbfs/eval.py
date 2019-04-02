def evaluation(node, goal):
    return hirustic(node, goal)


def hirustic(node, goal):
    h = 0
    unassigned_horses = node.horses.copy()
    assigned = [None] * len(node.horses)
    assigned_costs = [10000] * len(node.horses)
    while unassigned_horses:
        horse = unassigned_horses.pop()
        for i in range(0, len(goal.horses)):
            goal_horse = goal.horses[i]
            cost = abs(horse.x - goal_horse.x) + abs(horse.y - goal_horse.y)
            if assigned[i]:
                if assigned_costs[i] > cost:
                    unassigned_horses.insert(-1, assigned[i])
                    assigned[i] = horse
                    assigned_costs[i] = cost
            else:
                assigned[i] = horse
                assigned_costs[i] = cost
    tot = 0
    for a in assigned_costs:
        b = a / 3
        if b < 1:
            b += 2
        tot += b
    return tot

    # for horse in node.horses:
    #     cost = 1000
    #     for goal_horse in goal.horses:
    #         newCost = abs(horse.x - goal_horse.x) + abs(horse.y - goal_horse.y)
    #         if newCost < cost:
    #             cost = newCost
    #     h += cost / 3

    # return h
