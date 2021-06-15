import torch
import torch.nn as nn
import torch.nn.functional as F
from torchvision import models


class PrintLayer(nn.Module):
    def __init__(self):
        super(PrintLayer, self).__init__()

    def forward(self, x):
        # Do your print / debug stuff here
        print(x)
        return x

def set_parameter_requires_grad(model, feature_extracting):
    if feature_extracting:
        for param in model.parameters():
            param.requires_grad = False


class New_resnet(nn.Module):
    def __init__(self, pretrained_model, num_classes):
        super(New_resnet, self).__init__()
        self.pretrained = pretrained_model
        self.num_classes = num_classes
        self.num_features = list((list(pretrained_model.children()))[-2][-1].children())[-2].num_features
        self.newlayers = nn.Sequential(
            nn.BatchNorm2d(num_features=self.num_features),
            nn.Linear(in_features=self.num_features, out_features=512, bias=True),
            nn.BatchNorm2d(num_features=512),
            nn.ReLU(),
            nn.Dropout(0.5),
            nn.Linear(in_features=512, out_features=256, bias=True),
            nn.BatchNorm2d(num_features=256),
            nn.ReLU(),
            nn.Dropout(0.5),
            nn.Linear(in_features=256, out_features=128, bias=True),
            nn.BatchNorm2d(num_features=128),
            nn.ReLU(),
            nn.Dropout(0.5),
            nn.Linear(in_features=128, out_features=num_classes, bias=True),
            PrintLayer()
        )

    def forward(self, x):
        x = self.pretrained(x)
        logit = self.newlayers(x)
        prob = F.softmax(logit, dim=1)
        return logit, prob


def get_model(num_classes, feature_extract=True):
    model_pt = models.resnet101(pretrained=True)
    newmodel = torch.nn.Sequential(*(list(model_pt.children())[:-1]))
    print(newmodel)
    set_parameter_requires_grad(newmodel, feature_extract)
    NEW_RESNET = New_resnet(newmodel, num_classes)
    return NEW_RESNET
