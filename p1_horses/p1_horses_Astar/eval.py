def evaluation(node, goal):
    return hirustic(node, goal) + node.parent.dept + 1


def hirustic(node, goal):
    h = 0
    unassigned_horses = node.horses.copy()
    assigned = [None] * len(node.horses)
    assigned_costs = [10000] * len(node.horses)
    while unassigned_horses:
        horse = unassigned_horses.pop(0)
        cost_list = [None] * len(goal.horses)
        for i in range(0, len(goal.horses)):
            goal_horse = goal.horses[i]
            cost = abs(horse.x - goal_horse.x) + abs(horse.y - goal_horse.y)
            cost_list[i] = cost
        while 1:
            min_cost = min(cost_list)
            min_index = cost_list.index(min_cost)
            if assigned[min_index]:
                if assigned_costs[min_index] > min_cost:
                    unassigned_horses.append(assigned[min_index])
                    assigned[min_index] = horse
                    assigned_costs[min_index] = min_cost
                    break
                else:
                    cost_list[min_index] = 10000
            else:
                assigned[min_index] = horse
                assigned_costs[min_index] = min_cost
                break
    tot = 0
    for a in assigned_costs:
        if a == 0:
            continue
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
