# Test case for showcasing issues with "merge" behaviour

Updating our application from Spring boot 3.2.2 to 3.2.3 we encountered issued with persisting some entities.

We tracked the issue down to a change between hibernate 6.4.2-Final and 6.4.3-Final.

The `EntityTest` case succeeds when run with version 6.4.2-Final but fails on 6.4.3-Final.